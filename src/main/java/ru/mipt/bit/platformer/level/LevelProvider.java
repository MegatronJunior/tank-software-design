package ru.mipt.bit.platformer.level;

/**
 * Outbound application port for obtaining the initial contents of a game level.
 */
public interface LevelProvider {

    LevelData createLevel(int width, int height);

}
