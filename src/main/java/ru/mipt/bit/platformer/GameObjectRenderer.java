package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

public interface GameObjectRenderer extends Disposable {

    void draw(Batch batch);
}