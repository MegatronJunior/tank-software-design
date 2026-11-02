package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameLevelTest {

    @Test
    void addsAndRemovesObjects() {
        GameLevel level = new GameLevel();
        Tree tree = new Tree(new GridPoint2(1, 1));

        assertTrue(level.add(tree));
        assertFalse(level.add(tree));
        assertEquals(List.of(tree), level.objects());

        assertTrue(level.remove(tree));
        assertTrue(level.objects().isEmpty());
    }

    @Test
    void updatesTanksOnTick() {
        GameLevel level = new GameLevel();
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        level.add(tank);
        tank.startMovement(Direction.RIGHT);

        level.live(0.4f);

        assertEquals(
                new GridPoint2(2, 1),
                tank.currentCoordinates()
        );
    }

    @Test
    void allowsRemovingObjectsDuringTick() {
        GameLevel level = new GameLevel();

        GameObject object = new GameObject() {
            @Override
            public void live(float deltaTime) {
                level.remove(this);
            }
        };

        level.add(object);

        assertDoesNotThrow(() -> level.live(0.1f));
        assertTrue(level.objects().isEmpty());
    }


    @Test
    void notifiesListenerAboutExistingAndChangedObjects() {
        GameLevel level = new GameLevel();

        Tree firstTree = new Tree(new GridPoint2(1, 1));
        Tree secondTree = new Tree(new GridPoint2(2, 2));

        level.add(firstTree);

        List<GameObject> added = new ArrayList<>();
        List<GameObject> removed = new ArrayList<>();

        GameLevelListener listener = new GameLevelListener() {
            @Override
            public void onObjectAdded(GameObject object) {
                added.add(object);
            }

            @Override
            public void onObjectRemoved(GameObject object) {
                removed.add(object);
            }
        };

        level.addListener(listener);
        level.add(secondTree);
        level.remove(firstTree);

        assertEquals(List.of(firstTree, secondTree), added);
        assertEquals(List.of(firstTree), removed);

        level.removeListener(listener);
        level.remove(secondTree);

        assertEquals(List.of(firstTree), removed);
    }


    @Test
    void bulletDisappearsWhenHittingTree() {
        GameLevel level = new GameLevel();

        Tree tree = new Tree(new GridPoint2(2, 1));
        Bullet bullet = new Bullet(
                new GridPoint2(2, 1),
                Direction.RIGHT,
                0.1f,
                25
        );

        level.add(tree);
        level.add(bullet);

        assertEquals(List.of(tree), level.objects());
    }

    @Test
    void bulletDamagesAndDestroysTank() {
        GameLevel level = new GameLevel();

        Tank tank = new Tank(
                new GridPoint2(2, 1),
                Direction.LEFT,
                0.4f,
                20
        );

        Bullet bullet = new Bullet(
                new GridPoint2(2, 1),
                Direction.RIGHT,
                0.1f,
                25
        );

        level.add(tank);
        level.add(bullet);

        assertTrue(tank.isDestroyed());
        assertTrue(level.objects().isEmpty());
    }

    @Test
    void bulletsDestroyEachOtherAfterMovement() {
        GameLevel level = new GameLevel();

        Bullet first = new Bullet(
                new GridPoint2(0, 0),
                Direction.RIGHT,
                0.1f,
                25
        );

        Bullet second = new Bullet(
                new GridPoint2(2, 0),
                Direction.LEFT,
                0.1f,
                25
        );

        level.add(first);
        level.add(second);

        level.live(0.1f);

        assertTrue(level.objects().isEmpty());
    }


    @Test
    void removesBulletAfterLeavingField() {
        Field field = new Field(3, 3, List.of());
        GameLevel level = new GameLevel(field);

        Bullet bullet = new Bullet(
                new GridPoint2(2, 1),
                Direction.RIGHT,
                0.1f,
                25
        );

        level.add(bullet);
        level.live(0.1f);

        assertTrue(level.objects().isEmpty());
    }

    @Test
    void removesBulletSpawnedOutsideField() {
        Field field = new Field(3, 3, List.of());
        GameLevel level = new GameLevel(field);

        Bullet bullet = new Bullet(
                new GridPoint2(3, 1),
                Direction.RIGHT,
                0.1f,
                25
        );

        level.add(bullet);

        assertTrue(level.objects().isEmpty());
    }

}
