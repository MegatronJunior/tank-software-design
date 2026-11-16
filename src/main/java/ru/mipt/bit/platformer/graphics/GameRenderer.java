package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import java.util.List;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.GameLevelListener;


public class GameRenderer implements Disposable, GameLevelListener {

    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final List<GameObjectRenderer> objectRenderers;

    public GameRenderer(
            Batch batch,
            MapRenderer levelRenderer,
            List<GameObjectRenderer> objectRenderers
    ) {
        this.batch = batch;
        this.levelRenderer = levelRenderer;
        this.objectRenderers = List.copyOf(objectRenderers);
    }

    public void draw() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        levelRenderer.render();

        batch.begin();

        for (GameObjectRenderer renderer : objectRenderers) {
            renderer.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        for (GameObjectRenderer renderer : objectRenderers) {
            renderer.dispose();
        }
    }

    @Override
    public void onObjectAdded(GameObject object) {
        for (GameObjectRenderer renderer : objectRenderers) {
            renderer.onObjectAdded(object);
        }
    }

    @Override
    public void onObjectRemoved(GameObject object) {
        for (GameObjectRenderer renderer : objectRenderers) {
            renderer.onObjectRemoved(object);
        }
    }
}