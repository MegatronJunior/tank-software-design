package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.comand.GameAction;
import ru.mipt.bit.platformer.comand.MoveAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class FieldTest {

    @Test
    public void emptyCellInsideFieldIsFree() {
        Tree tree = new Tree(new GridPoint2(1, 3));
        Field field = new Field(10, 8, List.of(tree));

        assertTrue(field.isFree(new GridPoint2(2, 2)));
    }

    @Test
    public void cellWithTreeIsNotFree() {
        Tree tree = new Tree(new GridPoint2(1, 3));
        Field field = new Field(10, 8, List.of(tree));

        assertFalse(field.isFree(new GridPoint2(1, 3)));
    }

    @Test
    public void cellOutsideFieldIsNotFree() {
        Tree tree = new Tree(new GridPoint2(1, 3));
        Field field = new Field(10, 8, List.of(tree));

        assertFalse(field.isFree(new GridPoint2(-1, 0)));
    }

    @Test
    public void cellAtFieldSizeIsOutside() {
        Tree tree = new Tree(new GridPoint2(1, 3));
        Field field = new Field(10, 8, List.of(tree));

        assertFalse(field.isFree(new GridPoint2(10, 8)));
    }

    @Test
    public void cellWithSecondObstacleIsNotFree() {
        Tree firstTree = new Tree(new GridPoint2(1, 3));
        Tree secondTree = new Tree(new GridPoint2(4, 5));

        Field field = new Field(
                10,
                8,
                List.of(firstTree, secondTree)
        );

        assertFalse(field.isFree(new GridPoint2(4, 5)));
    }

    @Test
    void movingTankOccupiesBothCells() {
        Tank tank = new Tank(
                new GridPoint2(2, 2),
                Direction.RIGHT,
                0.4f
        );

        Field field = new Field(
                5,
                5,
                List.of(tank)
        );

        assertFalse(field.isFree(new GridPoint2(2, 2)));
        assertTrue(field.isFree(new GridPoint2(3, 2)));

        tank.startMovement(Direction.RIGHT);

        assertFalse(field.isFree(new GridPoint2(2, 2)));
        assertFalse(field.isFree(new GridPoint2(3, 2)));

        tank.continueMovement(0.4f);

        assertTrue(field.isFree(new GridPoint2(2, 2)));
        assertFalse(field.isFree(new GridPoint2(3, 2)));
    }

    @Test
    void tanksCannotMoveIntoSameCell() {
        Tank firstTank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        Tank secondTank = new Tank(
                new GridPoint2(3, 1),
                Direction.LEFT,
                0.4f
        );

        Field field = new Field(
                5,
                5,
                List.of(firstTank, secondTank)
        );

        GameAction firstMove =
                new MoveAction(firstTank, field, Direction.RIGHT);

        GameAction secondMove =
                new MoveAction(secondTank, field, Direction.LEFT);

        firstMove.execute();
        secondMove.execute();

        assertEquals(
                new GridPoint2(2, 1),
                firstTank.destinationCoordinates()
        );

        assertEquals(
                new GridPoint2(3, 1),
                secondTank.destinationCoordinates()
        );

        assertTrue(secondTank.hasFinishedCurrentMovement());
    }
}