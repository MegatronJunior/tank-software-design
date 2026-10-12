package ru.mipt.bit.platformer;

public interface LevelProvider {

    LevelData createLevel(int width, int height);

}
