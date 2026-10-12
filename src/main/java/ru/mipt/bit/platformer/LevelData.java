package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;

public class LevelData {

    private final int width;
    private final int height;
    private final GridPoint2 tankCoordinates;
    private final List<Tree> trees;

    public LevelData(
            int width,
            int height,
            GridPoint2 tankCoordinates,
            List<Tree> trees
    ) {
        this.width = width;
        this.height = height;
        this.tankCoordinates = new GridPoint2(tankCoordinates);
        this.trees = List.copyOf(trees);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public GridPoint2 tankCoordinates() {
        return new GridPoint2(tankCoordinates);
    }

    public List<Tree> trees() {
        return trees;
    }
}
