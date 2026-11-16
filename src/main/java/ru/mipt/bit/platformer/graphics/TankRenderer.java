
package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import java.util.LinkedHashSet;
import java.util.Set;
import ru.mipt.bit.platformer.model.GameObject;

public class TankRenderer implements GameObjectRenderer {

    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;
    private final Set<Tank> tanks = new LinkedHashSet<>();

    public TankRenderer(TiledMapTileLayer groundLayer) {
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
        for (Tank tank : tanks) {
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
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void onObjectAdded(GameObject object) {
        if (object instanceof Tank) {
            tanks.add((Tank) object);
        }
    }

    @Override
    public void onObjectRemoved(GameObject object) {
        if (object instanceof Tank) {
            tanks.remove((Tank) object);
        }
    }
}
