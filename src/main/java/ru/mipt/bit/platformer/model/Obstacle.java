package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

/**
 * Domain contract for objects occupying cells; not an infrastructure adapter.
 */
public interface Obstacle extends GameObject {

    boolean isHere(GridPoint2 coordinates);
}