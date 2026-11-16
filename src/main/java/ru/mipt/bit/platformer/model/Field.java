
package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Field implements GameLevelListener {

    private final int width;
    private final int height;
    private final Set<Obstacle> obstacles;

    public Field(int width, int height, List<Obstacle> obstacles) {
        this.width = width;
        this.height = height;
        this.obstacles = new LinkedHashSet<>(obstacles);
    }

    public boolean isFree(GridPoint2 coordinates) {
        return isInside(coordinates) && !isOccupied(coordinates);
    }

    public boolean isInside(GridPoint2 coordinates) {
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

    @Override
    public void onObjectAdded(GameObject object) {
        if (object instanceof Obstacle) {
            obstacles.add((Obstacle) object);
        }
    }

    @Override
    public void onObjectRemoved(GameObject object) {
        if (object instanceof Obstacle) {
            obstacles.remove((Obstacle) object);
        }
    }
}
