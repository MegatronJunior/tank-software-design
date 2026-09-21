package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class TankRenderer implements Disposable {

    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;

    public TankRenderer(TiledMapTileLayer groundLayer) {
        texture = new Texture("images/tank_blue.png");
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
        tileMovement = new TileMovement(
                groundLayer,
                Interpolation.smooth
        );
    }

    public void draw(Batch batch, Tank tank) {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                tank.currentCoordinates(),
                tank.destinationCoordinates(),
                tank.movementProgress()
        );

        drawTextureRegionUnscaled(
                batch,
                graphics,
                rectangle,
                tank.rotationAngle()
        );
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}