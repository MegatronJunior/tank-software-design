package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public interface Obstacle {

    boolean isHere(GridPoint2 coordinates);
}