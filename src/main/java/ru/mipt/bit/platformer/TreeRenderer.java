package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TreeRenderer implements GameObjectRenderer {

    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    private final TiledMapTileLayer groundLayer;
    private final Tree tree;

    public TreeRenderer(
            TiledMapTileLayer groundLayer,
            Tree tree
    ) {
        this.groundLayer = groundLayer;
        this.tree = tree;

        texture = new Texture("images/greenTree.png");
        textureRegion = new TextureRegion(texture);
        rectangle = createBoundingRectangle(textureRegion);
    }

    @Override
    public void draw(Batch batch) {
        moveRectangleAtTileCenter(
                groundLayer,
                rectangle,
                tree.coordinates()
        );

        drawTextureRegionUnscaled(
                batch,
                textureRegion,
                rectangle,
                0f
        );
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}