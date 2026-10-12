package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class Field {

    private final int width;
    private final int height;
    private final List<Obstacle> obstacles;

    public Field(int width, int height, List<Obstacle> obstacles) {
        this.width = width;
        this.height = height;
        this.obstacles = List.copyOf(obstacles);
    }

    public boolean isFree(GridPoint2 coordinates) {
        return isInside(coordinates) && !isOccupied(coordinates);
    }

    private boolean isInside(GridPoint2 coordinates) {
        return coordinates.x >= 0
                && coordinates.x < width
                && coordinates.y >= 0
                && coordinates.y < height;
    }

    private boolean isOccupied(GridPoint2 coordinates) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isHere(coordinates)) {
                return true;
            }
        }

        return false;
    }
}