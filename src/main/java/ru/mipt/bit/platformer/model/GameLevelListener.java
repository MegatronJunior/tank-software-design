package ru.mipt.bit.platformer.model;

/**
 * Outbound domain port for notifying consumers when level objects are added or removed.
 */
public interface GameLevelListener {

    void onObjectAdded(GameObject object);

    void onObjectRemoved(GameObject object);
}

