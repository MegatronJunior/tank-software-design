package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import java.util.List;

public class GameRenderer {

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
}