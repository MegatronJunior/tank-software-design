
package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Field;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RandomTankSpawnerTest {

    @Test
    void spawnsTanksInDistinctFreeCells() {
        LevelData level = new LevelData(
                4,
                3,
                new GridPoint2(0, 0),
                List.of(
                        new Tree(new GridPoint2(1, 1)),
                        new Tree(new GridPoint2(2, 2))
                )
        );

        RandomTankSpawner spawner =
                new RandomTankSpawner(new Random(42));

        List<Tank> tanks = spawner.spawn(level, 5, 0.4f);

        Set<GridPoint2> positions = tanks.stream()
                .map(Tank::currentCoordinates)
                .collect(Collectors.toSet());

        Field field = new Field(
                level.width(),
                level.height(),
                new ArrayList<>(level.trees())
        );

        assertEquals(5, tanks.size());
        assertEquals(5, positions.size());
        assertFalse(positions.contains(level.tankCoordinates()));
        assertTrue(positions.stream().allMatch(field::isFree));
    }

    @Test
    void rejectsSpawningWhenNotEnoughFreeCells() {
        LevelData level = new LevelData(
                2,
                1,
                new GridPoint2(0, 0),
                List.of(new Tree(new GridPoint2(1, 0)))
        );

        RandomTankSpawner spawner =
                new RandomTankSpawner(new Random(42));

        assertThrows(
                IllegalArgumentException.class,
                () -> spawner.spawn(level, 1, 0.4f)
        );
    }
}
