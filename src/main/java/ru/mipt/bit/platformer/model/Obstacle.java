package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public interface Obstacle extends GameObject {

    boolean isHere(GridPoint2 coordinates);
}