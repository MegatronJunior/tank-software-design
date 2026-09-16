package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(0, 1, 90f),
    DOWN(0, -1, -90f),
    LEFT(-1, 0, -180f),
    RIGHT(1, 0, 0f);

    private final GridPoint2 vector;
    private final float angle;

    Direction(int deltaX, int deltaY, float angle) {
        this.vector = new GridPoint2(deltaX, deltaY);
        this.angle = angle;
    }

    public GridPoint2 getVector() {
        return new GridPoint2(vector);
    }

    public float getAngle() {
        return angle;
    }
}