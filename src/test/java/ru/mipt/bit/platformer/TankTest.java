package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TankTest {

    @Test
    public void tankStartsAtInitialCoordinates() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        assertEquals(new GridPoint2(1, 1), tank.currentCoordinates());
    }

    @Test
    public void tankInitiallyHasFinishedMovement() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        assertTrue(tank.hasFinishedCurrentMovement());
    }

    @Test
    public void startMovementChangesDestination() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        tank.startMovementTo(new GridPoint2(2, 1));

        assertEquals(
                new GridPoint2(2, 1),
                tank.destinationCoordinates()
        );

        assertFalse(tank.hasFinishedCurrentMovement());
    }

    @Test
    public void tankReachesDestinationAfterMovement() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        tank.startMovementTo(new GridPoint2(2, 1));

        tank.continueMovement(0.4f);

        assertTrue(tank.hasFinishedCurrentMovement());
        assertEquals(
                new GridPoint2(2, 1),
                tank.currentCoordinates()
        );
    }

    @Test
    public void faceChangesRotationAngle() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        tank.face(Direction.UP);

        assertEquals(90f, tank.rotationAngle());
    }

    @Test
    public void currentCoordinatesReturnsCopy() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        GridPoint2 coordinates = tank.currentCoordinates();
        coordinates.set(100, 100);

        assertEquals(
                new GridPoint2(1, 1),
                tank.currentCoordinates()
        );
    }
}