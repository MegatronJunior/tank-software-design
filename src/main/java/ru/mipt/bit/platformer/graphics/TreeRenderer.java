
package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Tree;

import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;
import java.util.LinkedHashSet;
import java.util.Set;
import ru.mipt.bit.platformer.model.GameObject;

public class TreeRenderer implements GameObjectRenderer {

    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    private final TiledMapTileLayer groundLayer;
    private final Set<Tree> trees = new LinkedHashSet<>();

    public TreeRenderer(TiledMapTileLayer groundLayer) {
        this.groundLayer = groundLayer;

        texture = new Texture("images/greenTree.png");
        textureRegion = new TextureRegion(texture);
        rectangle = createBoundingRectangle(textureRegion);
    }

    @Override
    public void draw(Batch batch) {
        for (Tree tree : trees) {
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
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void onObjectAdded(GameObject object) {
        if (object instanceof Tree) {
            trees.add((Tree) object);
        }
    }

    @Override
    public void onObjectRemoved(GameObject object) {
        if (object instanceof Tree ) {
            trees.remove((Tree) object);
        }
    }
}
