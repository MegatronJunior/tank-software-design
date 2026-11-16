package ru.mipt.bit.platformer.model;

/**
 * Domain contract for objects updated by the game loop; not an external port.
 */
public interface GameObject {

    default void live(float deltaTime) {}
}
