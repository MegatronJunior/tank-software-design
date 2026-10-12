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

public class GameDesktopLauncher implements ApplicationListener {

    private static final float TANK_MOVEMENT_SPEED = 0.4f;

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

        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                TANK_MOVEMENT_SPEED
        );
        Tree tree = new Tree(new GridPoint2(1, 3));

        TankRenderer tankRenderer = new TankRenderer(
                groundLayer,
                tank
        );

        TreeRenderer treeRenderer = new TreeRenderer(
                groundLayer,
                tree
        );

        List<GameObjectRenderer> objectRenderers = List.of(
                tankRenderer,
                treeRenderer
        );

        gameRenderer = new GameRenderer(
                batch,
                levelRenderer,
                objectRenderers
        );

        Field field = new Field(
                groundLayer.getWidth(),
                groundLayer.getHeight(),
                List.of(tree)
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
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
