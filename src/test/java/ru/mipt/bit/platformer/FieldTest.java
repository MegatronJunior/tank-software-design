package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldTest {

    @Test
    public void emptyCellInsideFieldIsFree() {
        Tree tree = new Tree(new GridPoint2(1, 3));
        Field field = new Field(10, 8, tree);

        assertTrue(field.isFree(new GridPoint2(2, 2)));
    }

    @Test
    public void cellWithTreeIsNotFree() {
        Tree tree = new Tree(new GridPoint2(1, 3));
        Field field = new Field(10, 8, tree);

        assertFalse(field.isFree(new GridPoint2(1, 3)));
    }

    @Test
    public void cellOutsideFieldIsNotFree() {
        Tree tree = new Tree(new GridPoint2(1, 3));
        Field field = new Field(10, 8, tree);

        assertFalse(field.isFree(new GridPoint2(-1, 0)));
    }

    @Test
    public void cellAtFieldSizeIsOutside() {
        Tree tree = new Tree(new GridPoint2(1, 3));
        Field field = new Field(10, 8, tree);

        assertFalse(field.isFree(new GridPoint2(10, 8)));
    }
}