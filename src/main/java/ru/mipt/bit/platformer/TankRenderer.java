package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;


public class TankRenderer implements GameObjectRenderer {

    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;
    private final Tank tank;

    public TankRenderer(
            TiledMapTileLayer groundLayer,
            Tank tank
    ) {
        this.tank = tank;

        texture = new Texture("images/tank_blue.png");
        textureRegion = new TextureRegion(texture);
        rectangle = createBoundingRectangle(textureRegion);
        tileMovement = new TileMovement(
                groundLayer,
                Interpolation.smooth
        );
    }

    @Override
    public void draw(Batch batch) {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                tank.currentCoordinates(),
                tank.destinationCoordinates(),
                tank.movementProgress()
        );

        drawTextureRegionUnscaled(
                batch,
                textureRegion,
                rectangle,
                tank.rotationAngle()
        );
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}