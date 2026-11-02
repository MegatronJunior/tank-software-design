package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.model.GameLevel;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import ru.mipt.bit.platformer.command.GameAction;
import ru.mipt.bit.platformer.command.MoveAction;
import ru.mipt.bit.platformer.control.DefaultKeyBindings;
import ru.mipt.bit.platformer.control.KeyboardInputHandler;
import ru.mipt.bit.platformer.control.RandomTankControlStrategy;
import ru.mipt.bit.platformer.control.TankControlStrategy;
import ru.mipt.bit.platformer.graphics.GameObjectRenderer;
import ru.mipt.bit.platformer.graphics.GameRenderer;
import ru.mipt.bit.platformer.graphics.TankRenderer;
import ru.mipt.bit.platformer.graphics.TreeRenderer;
import ru.mipt.bit.platformer.level.*;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Field;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.GameLevel;
import ru.mipt.bit.platformer.graphics.BulletRenderer;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;
import java.util.EnumMap;
import ru.mipt.bit.platformer.command.ShootAction;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float TANK_MOVEMENT_SPEED = 0.4f;
    private static final float BULLET_MOVEMENT_SPEED = 0.1f;
    private static final int BULLET_DAMAGE = 25;


    private final boolean useFileLevel;

    public GameDesktopLauncher(boolean useFileLevel) {
        this.useFileLevel = useFileLevel;
    }


    private Batch batch;

    private TiledMap tiledMap;
    private GameRenderer gameRenderer;

    private GameController gameController;


    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        tiledMap = new TmxMapLoader().load("level.tmx");
        MapRenderer levelRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);

        LevelData level = createLevel(groundLayer);

        Tank playerTank = new Tank(
                level.tankCoordinates(),
                Direction.RIGHT,
                TANK_MOVEMENT_SPEED
        );

        RandomTankSpawner tankSpawner =
                new RandomTankSpawner(new Random());

        List<Tank> aiTanks = tankSpawner.spawn(
                level,
                3,
                TANK_MOVEMENT_SPEED
        );

        List<Tank> tanks = new ArrayList<>();
        tanks.add(playerTank);
        tanks.addAll(aiTanks);


        List<GameObjectRenderer> objectRenderers = List.of(
                new TankRenderer(groundLayer),
                new TreeRenderer(groundLayer),
                new BulletRenderer(groundLayer)
        );

        gameRenderer = new GameRenderer(
                batch,
                levelRenderer,
                objectRenderers
        );

        List<Obstacle> obstacles =
                new ArrayList<>(level.trees());

        obstacles.addAll(tanks);


        Field field = new Field(
                level.width(),
                level.height(),
                List.of()
        );

        GameLevel gameLevel = new GameLevel(field);
        gameLevel.addAll(level.trees());
        gameLevel.addAll(tanks);

        gameLevel.addListener(field);
        gameLevel.addListener(gameRenderer);

        List<TankControlStrategy> controlStrategies =
                createControlStrategies(
                        playerTank,
                        aiTanks,
                        field,
                        gameLevel
                );

        gameController = new GameController(
                gameLevel,
                controlStrategies
        );

    }


    private List<TankControlStrategy> createControlStrategies(
            Tank playerTank,
            List<Tank> aiTanks,
            Field field,
            GameLevel gameLevel
    ) {
        Map<Direction, GameAction> playerMovementActions =
                createMovementActions(playerTank, field);

        GameAction playerShootAction = new ShootAction(
                playerTank,
                gameLevel,
                BULLET_MOVEMENT_SPEED,
                BULLET_DAMAGE
        );

        KeyboardInputHandler keyboardControl =
                new KeyboardInputHandler(
                        key -> Gdx.input.isKeyPressed(key),
                        DefaultKeyBindings.create(
                                playerMovementActions,
                                playerShootAction
                        )
                );

        List<TankControlStrategy> strategies =
                new ArrayList<>();

        strategies.add(keyboardControl);

        for (Tank aiTank : aiTanks) {
            GameAction aiShootAction = new ShootAction(
                    aiTank,
                    gameLevel,
                    BULLET_MOVEMENT_SPEED,
                    BULLET_DAMAGE
            );

            Map<Direction, GameAction> aiMovementActions =
                    createMovementActions(aiTank, field);

            strategies.add(
                    new RandomTankControlStrategy(
                            aiTank,
                            new Random(),
                            aiMovementActions,
                            aiShootAction
                    )
            );
        }

        return List.copyOf(strategies);
    }

    private LevelData createLevel(TiledMapTileLayer groundLayer) {
        LevelProvider levelProvider;

        if (useFileLevel) {
            levelProvider = new FileLevelProvider(
                    Gdx.files.internal("levels/level1.txt")
            );
        } else {
            levelProvider = new RandomLevelProvider(
                    new Random(), 10
            );
        }

        return levelProvider.createLevel(
                groundLayer.getWidth(),
                groundLayer.getHeight()
        );
    }

    private Map<Direction, GameAction> createMovementActions(
            Tank tank,
            Field field
    ) {
        Map<Direction, GameAction> actions =
                new EnumMap<>(Direction.class);

        for (Direction direction : Direction.values()) {
            actions.put(
                    direction,
                    new MoveAction(tank, field, direction)
            );
        }

        return Map.copyOf(actions);
    }


    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        gameController.update(deltaTime);

        gameRenderer.draw();
    }


    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        tiledMap.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);

        boolean useFileLevel =
                args.length > 0 && args[0].equals("file");

        new Lwjgl3Application(
                new GameDesktopLauncher(useFileLevel),
                config
        );

    }
}
