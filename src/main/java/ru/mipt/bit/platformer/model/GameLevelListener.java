package ru.mipt.bit.platformer.model;

public interface GameLevelListener {

    void onObjectAdded(GameObject object);

    void onObjectRemoved(GameObject object);
}

