package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public final class HealthBarRendererDecorator implements GameObjectRenderer {

    private static final float BAR_WIDTH_RATIO = 0.65f;
    private static final float BAR_HEIGHT_RATIO = 0.08f;
    private static final float BAR_VERTICAL_OFFSET_RATIO = 0.4f;
    private static final float BORDER_WIDTH = 2f;

    private final GameObjectRenderer delegate;
    private final TileMovement tileMovement;
    private final Rectangle barRectangle;
    private final Texture pixel;
    private final Set<Tank> tanks = new LinkedHashSet<>();
    private final float verticalOffset;

    private boolean visible;

    public HealthBarRendererDecorator(
            GameObjectRenderer delegate,
            TiledMapTileLayer groundLayer
    ) {
        this.delegate = Objects.requireNonNull(delegate);
        Objects.requireNonNull(groundLayer);

        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        barRectangle = new Rectangle(
                0f,
                0f,
                groundLayer.getTileWidth() * BAR_WIDTH_RATIO,
                groundLayer.getTileHeight() * BAR_HEIGHT_RATIO
        );
        verticalOffset = groundLayer.getTileHeight() * BAR_VERTICAL_OFFSET_RATIO;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        pixel = new Texture(pixmap);
        pixmap.dispose();
    }

    public void toggleVisibility() {
        visible = !visible;
    }

    @Override
    public void draw(Batch batch) {
        delegate.draw(batch);

        if (!visible) {
            return;
        }

        Color previousColor = new Color(batch.getColor());

        for (Tank tank : tanks) {
            tileMovement.moveRectangleBetweenTileCenters(
                    barRectangle,
                    tank.currentCoordinates(),
                    tank.destinationCoordinates(),
                    tank.movementProgress()
            );

            float x = barRectangle.x;
            float y = barRectangle.y + verticalOffset;
            float width = barRectangle.width;
            float height = barRectangle.height;

            batch.setColor(Color.BLACK);
            batch.draw(pixel, x, y, width, height);

            batch.setColor(Color.GREEN);
            batch.draw(
                    pixel,
                    x + BORDER_WIDTH,
                    y + BORDER_WIDTH,
                    (width - 2f * BORDER_WIDTH) * tank.healthFraction(),
                    height - 2f * BORDER_WIDTH
            );
        }

        batch.setColor(previousColor);
    }

    @Override
    public void onObjectAdded(GameObject object) {
        delegate.onObjectAdded(object);

        if (object instanceof Tank) {
            tanks.add((Tank) object);
        }
    }

    @Override
    public void onObjectRemoved(GameObject object) {
        delegate.onObjectRemoved(object);

        if (object instanceof Tank) {
            tanks.remove((Tank) object);
        }
    }

    @Override
    public void dispose() {
        delegate.dispose();
        pixel.dispose();
    }
}
