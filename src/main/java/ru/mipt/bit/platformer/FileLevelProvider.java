
package ru.mipt.bit.platformer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class FileLevelProvider implements LevelProvider {

    private final FileHandle file;

    public FileLevelProvider(FileHandle file) {
        this.file = file;
    }

    @Override
    public LevelData createLevel(int width, int height) {
        String[] rows = file.readString("UTF-8").split("\\R");

        if (rows.length != height) {
            throw new IllegalArgumentException(
                    "Invalid level height"
            );
        }

        List<Tree> trees = new ArrayList<>();
        GridPoint2 tankCoordinates = null;

        for (int row = 0; row < height; row++) {
            if (rows[row].length() != width) {
                throw new IllegalArgumentException(
                        "Invalid level width"
                );
            }

            for (int x = 0; x < width; x++) {
                char symbol = rows[row].charAt(x);
                int y = height - 1 - row;

                switch (symbol) {
                    case 'T':
                        trees.add(new Tree(new GridPoint2(x, y)));
                        break;

                    case 'X':
                        if (tankCoordinates != null) {
                            throw new IllegalArgumentException(
                                    "Multiple tanks found"
                            );
                        }
                        tankCoordinates = new GridPoint2(x, y);
                        break;

                    case '_':
                        break;

                    default:
                        throw new IllegalArgumentException(
                                "Unknown symbol: " + symbol
                        );
                }
            }
        }

        if (tankCoordinates == null) {
            throw new IllegalArgumentException(
                    "Tank not found"
            );
        }

        return new LevelData(
                width,
                height,
                tankCoordinates,
                trees
        );
    }
}
