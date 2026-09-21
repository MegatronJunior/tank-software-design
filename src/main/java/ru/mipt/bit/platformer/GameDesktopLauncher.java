package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;
import java.util.Set;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float BLUE_TANK_MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Texture blueTankTexture;
    private TextureRegion tankGraphics;
    private Rectangle tankRectangle;
    private Tank tank;
    private Tree tree;
    private Field field;

    private KeyboardInputHandler inputHandler;
    private GameController gameController;

    private Texture greenTreeTexture;
    private TextureRegion treeGraphics;
    private Rectangle treeRectangle;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        blueTankTexture = new Texture("images/tank_blue.png");
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        tankGraphics = new TextureRegion(blueTankTexture);
        tankRectangle = createBoundingRectangle(tankGraphics);

        tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                BLUE_TANK_MOVEMENT_SPEED
        );


        greenTreeTexture = new Texture("images/greenTree.png");
        treeGraphics = new TextureRegion(greenTreeTexture);
        tree = new Tree(new GridPoint2(1, 3));
        treeRectangle = createBoundingRectangle(treeGraphics);
        moveRectangleAtTileCenter(groundLayer, treeRectangle, tree.coordinates());

        field = new Field(
                groundLayer.getWidth(),
                groundLayer.getHeight(),
                tree
        );

        inputHandler = new KeyboardInputHandler();
        gameController = new GameController(tank, field);

    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Set<GameAction> actions = inputHandler.readActions();
        gameController.update(actions, deltaTime);

        draw();
    }


    private void draw() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        tileMovement.moveRectangleBetweenTileCenters(
                tankRectangle,
                tank.currentCoordinates(),
                tank.destinationCoordinates(),
                tank.movementProgress()
        );

        levelRenderer.render();

        batch.begin();

        drawTextureRegionUnscaled(
                batch,
                tankGraphics,
                tankRectangle,
                tank.rotationAngle()
        );

        drawTextureRegionUnscaled(
                batch,
                treeGraphics,
                treeRectangle,
                0f
        );

        batch.end();
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
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        greenTreeTexture.dispose();
        blueTankTexture.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
