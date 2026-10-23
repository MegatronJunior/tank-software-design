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

    @Test
    public void updatesMovementOfAllTanks() {
        Tank firstTank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        Tank secondTank = new Tank(
                new GridPoint2(3, 3),
                Direction.UP,
                0.4f
        );

        GameController controller = new GameController(
                List.of(firstTank, secondTank)
        );

        firstTank.startMovement(Direction.RIGHT);
        secondTank.startMovement(Direction.UP);

        controller.update(List.of(), 0.4f);

        assertEquals(
                new GridPoint2(2, 1),
                firstTank.currentCoordinates()
        );

        assertEquals(
                new GridPoint2(3, 4),
                secondTank.currentCoordinates()
        );
    }


    @Test
    void executesActionsFromAllControlStrategies() {
        Tank playerTank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        Tank aiTank = new Tank(
                new GridPoint2(4, 1),
                Direction.LEFT,
                0.4f
        );

        Field field = new Field(
                6,
                4,
                List.of(playerTank, aiTank)
        );

        TankControlStrategy playerControl = () -> List.of(
                new MoveAction(playerTank, field, Direction.UP)
        );

        TankControlStrategy aiControl = () -> List.of(
                new MoveAction(aiTank, field, Direction.LEFT)
        );

        GameController controller = new GameController(
                List.of(playerTank, aiTank),
                List.of(playerControl, aiControl)
        );

        controller.update(0.4f);

        assertEquals(
                new GridPoint2(1, 2),
                playerTank.currentCoordinates()
        );

        assertEquals(
                new GridPoint2(3, 1),
                aiTank.currentCoordinates()
        );
    }


    @Test
    void competingTankCommandsCannotReserveSameCell() {
        Tank firstTank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        Tank secondTank = new Tank(
                new GridPoint2(3, 1),
                Direction.UP,
                0.4f
        );

        Field field = new Field(
                5,
                3,
                List.of(firstTank, secondTank)
        );

        TankControlStrategy firstControl = () -> List.of(
                new MoveAction(firstTank, field, Direction.RIGHT)
        );

        TankControlStrategy secondControl = () -> List.of(
                new MoveAction(secondTank, field, Direction.LEFT)
        );

        GameController controller = new GameController(
                List.of(firstTank, secondTank),
                List.of(firstControl, secondControl)
        );

        controller.update(0f);

        assertEquals(
                new GridPoint2(2, 1),
                firstTank.destinationCoordinates()
        );

        assertFalse(firstTank.hasFinishedCurrentMovement());

        assertEquals(
                new GridPoint2(3, 1),
                secondTank.destinationCoordinates()
        );

        assertTrue(secondTank.hasFinishedCurrentMovement());
        assertEquals(-180f, secondTank.rotationAngle());
    }

}