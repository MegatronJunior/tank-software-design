package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


public class GameControllerTest {

    @Test
    public void moveUpStartsMovementToFreeCell() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        Tree tree = new Tree(new GridPoint2(5, 5));
        Field field = new Field(10, 8, List.of(tree));

        GameController controller = new GameController(tank);

        GameAction moveUp =
                new MoveAction(tank, field, Direction.UP);

        controller.update(
                List.of(moveUp),
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
        Field field = new Field(10, 8, List.of(tree));

        GameController controller = new GameController(tank);

        GameAction moveUp =
                new MoveAction(tank, field, Direction.UP);

        controller.update(
                List.of(moveUp),
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
        Field field = new Field(10, 8, List.of(tree));

        GameController controller = new GameController(tank);
        GameAction moveLeft =
                new MoveAction(tank, field, Direction.LEFT);

        controller.update(
                List.of(moveLeft),
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