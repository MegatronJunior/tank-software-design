package ru.mipt.bit.platformer.model;

public interface GameObject {

    default void live(float deltaTime) {}
}
