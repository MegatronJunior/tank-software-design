package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;


public class Field {

    private final int width;
    private final int height;
    private final Tree tree;

    public Field(int width, int height, Tree tree) {
        this.width = width;
        this.height = height;
        this.tree = tree;
    }

    public boolean isFree(GridPoint2 coordinates) {
        return isInside(coordinates) && !tree.isHere(coordinates);
    }

    private boolean isInside(GridPoint2 coordinates) {
        return coordinates.x >= 0
                && coordinates.x < width
                && coordinates.y >= 0
                && coordinates.y < height;
    }
}
