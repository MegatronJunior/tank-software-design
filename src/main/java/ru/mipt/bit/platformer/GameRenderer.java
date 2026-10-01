package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameRenderer {

    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final TankRenderer tankRenderer;
    private final TreeRenderer treeRenderer;

    public GameRenderer(
            Batch batch,
            MapRenderer levelRenderer,
            TankRenderer tankRenderer,
            TreeRenderer treeRenderer
    ) {
        this.batch = batch;
        this.levelRenderer = levelRenderer;
        this.tankRenderer = tankRenderer;
        this.treeRenderer = treeRenderer;
    }

    public void draw(Tank tank, Tree tree) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        levelRenderer.render();

        batch.begin();

        tankRenderer.draw(batch, tank);
        treeRenderer.draw(batch, tree);

        batch.end();
    }
}