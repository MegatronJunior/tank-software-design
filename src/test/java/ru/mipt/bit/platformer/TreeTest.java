package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TreeTest {

    @Test
    public void treeHasCorrectCoordinates() {
        Tree tree = new Tree(new GridPoint2(1, 3));

        assertEquals(new GridPoint2(1, 3), tree.coordinates());
    }

    @Test
    public void isHereReturnsTrueForTreeCoordinates() {
        Tree tree = new Tree(new GridPoint2(1, 3));

        assertTrue(tree.isHere(new GridPoint2(1, 3)));
    }

    @Test
    public void isHereReturnsFalseForOtherCoordinates() {
        Tree tree = new Tree(new GridPoint2(1, 3));

        assertFalse(tree.isHere(new GridPoint2(2, 3)));
    }

    @Test
    public void coordinatesReturnsCopy() {
        Tree tree = new Tree(new GridPoint2(1, 3));

        GridPoint2 coordinates = tree.coordinates();
        coordinates.set(100, 100);

        assertEquals(
                new GridPoint2(1, 3),
                tree.coordinates()
        );
    }

}