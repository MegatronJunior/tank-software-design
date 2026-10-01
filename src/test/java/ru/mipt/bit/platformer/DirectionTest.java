package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {

    @Test
    public void directionsHaveCorrectVectors() {
        assertEquals(new GridPoint2(0, 1), Direction.UP.getVector());
        assertEquals(new GridPoint2(0, -1), Direction.DOWN.getVector());
        assertEquals(new GridPoint2(-1, 0), Direction.LEFT.getVector());
        assertEquals(new GridPoint2(1, 0), Direction.RIGHT.getVector());
    }

    @Test
    public void directionsHaveCorrectAngles() {
        assertEquals(90f, Direction.UP.getAngle());
        assertEquals(-90f, Direction.DOWN.getAngle());
        assertEquals(-180f, Direction.LEFT.getAngle());
        assertEquals(0f, Direction.RIGHT.getAngle());
    }

    @Test
    public void getVectorReturnsCopy() {
        GridPoint2 vector = Direction.UP.getVector();

        vector.set(100, 100);

        assertEquals(
                new GridPoint2(0, 1),
                Direction.UP.getVector()
        );
    }
}