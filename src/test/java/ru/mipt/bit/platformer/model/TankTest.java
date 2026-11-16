package ru.mipt.bit.platformer.model;

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
    public void startMovementSetsNeighbouringDestination() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        tank.startMovement(Direction.RIGHT);

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

        tank.startMovement(Direction.RIGHT);

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

    @Test
    void cannotStartNewMovementWhileAlreadyMoving() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        tank.startMovement(Direction.RIGHT);
        tank.startMovement(Direction.UP);

        assertEquals(
                new GridPoint2(2, 1),
                tank.destinationCoordinates()
        );

        assertFalse(tank.hasFinishedCurrentMovement());
        assertEquals(0f, tank.rotationAngle());
    }

    @Test
    void rejectsZeroAndNegativeMovementSpeed() {
        assertThrows(IllegalArgumentException.class,
                () -> new Tank(
                        new GridPoint2(1, 1),
                        Direction.RIGHT,
                        0f
                ));

        assertThrows(IllegalArgumentException.class,
                () -> new Tank(
                        new GridPoint2(1, 1),
                        Direction.RIGHT,
                        -0.4f
                ));
    }

    @Test
    void rejectsNonFiniteMovementSpeed() {
        assertThrows(IllegalArgumentException.class,
                () -> new Tank(
                        new GridPoint2(1, 1),
                        Direction.RIGHT,
                        Float.NaN
                ));

        assertThrows(IllegalArgumentException.class,
                () -> new Tank(
                        new GridPoint2(1, 1),
                        Direction.RIGHT,
                        Float.POSITIVE_INFINITY
                ));
    }


    @Test
    void tankTakesDamageAndCanBeDestroyed() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        assertEquals(100, tank.health());
        assertFalse(tank.isDestroyed());

        tank.takeDamage(25);

        assertEquals(75, tank.health());
        assertFalse(tank.isDestroyed());

        tank.takeDamage(75);

        assertEquals(0, tank.health());
        assertTrue(tank.isDestroyed());

        tank.takeDamage(25);

        assertEquals(0, tank.health());
    }

    @Test
    void rejectsInvalidDamage() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> tank.takeDamage(0)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> tank.takeDamage(-10)
        );

        assertEquals(100, tank.health());
    }

    @Test
    void createsTankWithCustomHealth() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f,
                200
        );

        assertEquals(200, tank.health());

        tank.takeDamage(50);

        assertEquals(150, tank.health());
    }
}