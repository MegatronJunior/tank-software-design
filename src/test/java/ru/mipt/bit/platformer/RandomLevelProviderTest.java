package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RandomLevelProviderTest {

    @Test
    public void createsRequestedNumberOfTrees() {
        RandomLevelProvider provider =
                new RandomLevelProvider(new Random(42), 10);

        LevelData level = provider.createLevel(10, 8);

        assertEquals(10, level.trees().size());
        assertEquals(10, level.width());
        assertEquals(8, level.height());
    }

    @Test
    public void allObjectsAreInsideField() {
        RandomLevelProvider provider =
                new RandomLevelProvider(new Random(42), 20);

        LevelData level = provider.createLevel(10, 8);

        GridPoint2 tank = level.tankCoordinates();

        assertTrue(isInside(tank, 10, 8));

        for (Tree tree : level.trees()) {
            assertTrue(isInside(tree.coordinates(), 10, 8));
        }
    }

    @Test
    public void objectsDoNotOverlap() {
        RandomLevelProvider provider =
                new RandomLevelProvider(new Random(42), 20);

        LevelData level = provider.createLevel(10, 8);

        Set<GridPoint2> occupiedCells = new HashSet<>();

        occupiedCells.add(level.tankCoordinates());

        for (Tree tree : level.trees()) {
            assertTrue(occupiedCells.add(tree.coordinates()));
        }

        assertEquals(21, occupiedCells.size());
    }

    @Test
    public void rejectsTooManyTrees() {
        RandomLevelProvider provider =
                new RandomLevelProvider(new Random(42), 12);

        assertThrows(
                IllegalArgumentException.class,
                () -> provider.createLevel(4, 3)
        );
    }

    private boolean isInside(
            GridPoint2 point, int width, int height
    ) {
        return point.x >= 0 && point.x < width
                && point.y >= 0 && point.y < height;
    }
}
