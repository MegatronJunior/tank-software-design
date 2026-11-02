package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.GameObject;

import java.util.LinkedHashSet;
import java.util.Set;

public final class BulletRenderer implements GameObjectRenderer {

    private final Set<Bullet> bullets = new LinkedHashSet<>();
    private final TiledMapTileLayer groundLayer;
    private final Texture texture;

    public BulletRenderer(TiledMapTileLayer groundLayer) {
        this.groundLayer = groundLayer;

        Pixmap pixmap = new Pixmap(
                16,
                16,
                Pixmap.Format.RGBA8888
        );

        pixmap.setColor(Color.YELLOW);
        pixmap.fillCircle(8, 8, 6);

        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void onObjectAdded(GameObject object) {
        if (object instanceof Bullet) {
            bullets.add((Bullet) object);
        }
    }

    @Override
    public void onObjectRemoved(GameObject object) {
        if (object instanceof Bullet) {
            bullets.remove((Bullet) object);
        }
    }


    @Override
    public void draw(Batch batch) {
        float tileWidth = groundLayer.getTileWidth();
        float tileHeight = groundLayer.getTileHeight();

        float bulletWidth = tileWidth * 0.25f;
        float bulletHeight = tileHeight * 0.25f;

        for (Bullet bullet : bullets) {
            GridPoint2 coordinates = bullet.coordinates();
            GridPoint2 direction = bullet.direction().getVector();

            float progress = bullet.movementProgress();

            float positionX = coordinates.x + direction.x * progress;
            float positionY = coordinates.y + direction.y * progress;

            float x = positionX * tileWidth
                    + (tileWidth - bulletWidth) / 2f;

            float y = positionY * tileHeight
                    + (tileHeight - bulletHeight) / 2f;

            batch.draw(
                    texture,
                    x,
                    y,
                    bulletWidth,
                    bulletHeight
            );
        }
    }


    @Override
    public void dispose() {
        texture.dispose();
    }
}
