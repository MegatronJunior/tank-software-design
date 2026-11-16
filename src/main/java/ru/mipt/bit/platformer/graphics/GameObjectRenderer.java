package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.GameLevelListener;

/**
 * Infrastructure rendering contract. It is internal to the presentation layer, not a domain port.
 */
public interface GameObjectRenderer
        extends Disposable, GameLevelListener {

    void draw(Batch batch);
}