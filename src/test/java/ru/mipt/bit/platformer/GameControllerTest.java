package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    @Test
    public void moveUpStartsMovementToFreeCell() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        Tree tree = new Tree(new GridPoint2(5, 5));
        Field field = new Field(10, 8, tree);

        GameController controller = new GameController(tank, field);

        controller.update(
                EnumSet.of(GameAction.MOVE_UP),
                0f
        );

        assertEquals(
                new GridPoint2(1, 2),
                tank.destinationCoordinates()
        );

        assertFalse(tank.hasFinishedCurrentMovement());
        assertEquals(90f, tank.rotationAngle());
    }

    @Test
    public void tankDoesNotMoveIntoTreeButTurns() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        Tree tree = new Tree(new GridPoint2(1, 2));
        Field field = new Field(10, 8, tree);

        GameController controller = new GameController(tank, field);

        controller.update(
                EnumSet.of(GameAction.MOVE_UP),
                0f
        );

        assertEquals(
                new GridPoint2(1, 1),
                tank.destinationCoordinates()
        );

        assertTrue(tank.hasFinishedCurrentMovement());
        assertEquals(90f, tank.rotationAngle());
    }

    @Test
    public void tankDoesNotMoveOutsideFieldButTurns() {
        Tank tank = new Tank(
                new GridPoint2(0, 0),
                Direction.RIGHT,
                0.4f
        );

        Tree tree = new Tree(new GridPoint2(5, 5));
        Field field = new Field(10, 8, tree);

        GameController controller = new GameController(tank, field);

        controller.update(
                EnumSet.of(GameAction.MOVE_LEFT),
                0f
        );

        assertEquals(
                new GridPoint2(0, 0),
                tank.destinationCoordinates()
        );

        assertTrue(tank.hasFinishedCurrentMovement());
        assertEquals(-180f, tank.rotationAngle());
    }
}