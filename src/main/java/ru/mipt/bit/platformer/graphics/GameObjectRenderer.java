package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.GameLevelListener;

public interface GameObjectRenderer
        extends Disposable, GameLevelListener {

    void draw(Batch batch);
}