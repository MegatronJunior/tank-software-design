package ru.mipt.bit.platformer;

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
import com.badlogic.gdx.math.GridPoint2;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;


public class GameDesktopLauncher implements ApplicationListener {

    private static final float TANK_MOVEMENT_SPEED = 0.4f;


    private final boolean useFileLevel;

    public GameDesktopLauncher(boolean useFileLevel) {
        this.useFileLevel = useFileLevel;
    }


    private Batch batch;

    private TiledMap tiledMap;
    private GameRenderer gameRenderer;


    private KeyboardInputHandler inputHandler;
    private GameController gameController;


    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        tiledMap = new TmxMapLoader().load("level.tmx");
        MapRenderer levelRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);

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

        LevelData level = levelProvider.createLevel(
                groundLayer.getWidth(),
                groundLayer.getHeight()
        );

        Tank tank = new Tank(
                level.tankCoordinates(),
                Direction.RIGHT,
                TANK_MOVEMENT_SPEED
        );

        List<GameObjectRenderer> objectRenderers =
                new ArrayList<>();

        objectRenderers.add(
                new TankRenderer(groundLayer, tank)
        );

        objectRenderers.add(
                new TreeRenderer(groundLayer, level.trees())
        );

        gameRenderer = new GameRenderer(
                batch,
                levelRenderer,
                objectRenderers
        );

        List<Obstacle> obstacles =
                new ArrayList<>(level.trees());

        Field field = new Field(
                level.width(),
                level.height(),
                obstacles
        );

        Map<Direction, GameAction> movementActions =
                createMovementActions(tank, field);



        inputHandler = new KeyboardInputHandler(
                key -> Gdx.input.isKeyPressed(key),
                DefaultKeyBindings.create(movementActions)
        );

        gameController = new GameController(tank);

    }

    private Map<Direction, GameAction> createMovementActions(
            Tank tank,
            Field field
    ) {
        return Map.of(
                Direction.UP,
                new MoveAction(tank, field, Direction.UP),

                Direction.DOWN,
                new MoveAction(tank, field, Direction.DOWN),

                Direction.LEFT,
                new MoveAction(tank, field, Direction.LEFT),

                Direction.RIGHT,
                new MoveAction(tank, field, Direction.RIGHT)
        );
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        List<GameAction> actions = inputHandler.readActions();
        gameController.update(actions, deltaTime);

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
