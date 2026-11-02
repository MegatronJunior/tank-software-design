
package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Field;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class RandomTankSpawner {

    private final Random random;

    public RandomTankSpawner(Random random) {
        this.random = Objects.requireNonNull(random);
    }

    public List<Tank> spawn(
            LevelData level,
            int tankCount,
            float movementSpeed
    ) {
        Objects.requireNonNull(level);

        if (tankCount < 0) {
            throw new IllegalArgumentException(
                    "Tank count cannot be negative"
            );
        }

        if (!Float.isFinite(movementSpeed) || movementSpeed <= 0f) {
            throw new IllegalArgumentException(
                    "Movement speed must be positive and finite"
            );
        }

        Field field = new Field(
                level.width(),
                level.height(),
                new ArrayList<>(level.trees())
        );

        List<GridPoint2> availableCells = new ArrayList<>();

        for (int x = 0; x < level.width(); x++) {
            for (int y = 0; y < level.height(); y++) {
                GridPoint2 cell = new GridPoint2(x, y);

                if (field.isFree(cell)
                        && !cell.equals(level.tankCoordinates())) {
                    availableCells.add(cell);
                }
            }
        }

        if (tankCount > availableCells.size()) {
            throw new IllegalArgumentException(
                    "Not enough free cells for tanks"
            );
        }

        Collections.shuffle(availableCells, random);

        List<Tank> tanks = new ArrayList<>();
        Direction[] directions = Direction.values();

        for (int i = 0; i < tankCount; i++) {
            Direction direction =
                    directions[random.nextInt(directions.length)];

            tanks.add(new Tank(
                    availableCells.get(i),
                    direction,
                    movementSpeed
            ));
        }

        return List.copyOf(tanks);
    }
}
