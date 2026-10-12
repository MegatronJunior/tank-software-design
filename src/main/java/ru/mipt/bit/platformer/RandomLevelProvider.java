
package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomLevelProvider implements LevelProvider {

    private final Random random;
    private final int treeCount;

    public RandomLevelProvider(Random random, int treeCount) {
        this.random = random;
        this.treeCount = treeCount;
    }

    @Override
    public LevelData createLevel(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    "Field dimensions must be positive"
            );
        }

        if (treeCount < 0 || treeCount >= width * height) {
            throw new IllegalArgumentException(
                    "Invalid number of trees"
            );
        }

        List<GridPoint2> cells = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells.add(new GridPoint2(x, y));
            }
        }

        Collections.shuffle(cells, random);

        GridPoint2 tankCoordinates = cells.get(0);

        List<Tree> trees = new ArrayList<>();

        for (int i = 1; i <= treeCount; i++) {
            trees.add(new Tree(cells.get(i)));
        }

        return new LevelData(
                width,
                height,
                tankCoordinates,
                trees
        );
    }
}
