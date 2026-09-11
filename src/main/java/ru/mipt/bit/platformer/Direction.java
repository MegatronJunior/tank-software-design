package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(0, 1, 90f),
    DOWN(0, -1, -90f),
    LEFT(-1, 0, -180f),
    RIGHT(1, 0, 0f);

    private final int deltaX;
    private final int deltaY;
    private final float angle;

    Direction(int deltaX, int deltaY, float angle) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.angle = angle;
    }

    public GridPoint2 nextCoordinates(GridPoint2 coordinates) {
        return new GridPoint2(coordinates).add(deltaX, deltaY);
    }

    public float getAngle() {
        return angle;
    }

}
