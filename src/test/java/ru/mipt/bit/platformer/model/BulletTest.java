package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulletTest {

    @Test
    void startsAtGivenCoordinates() {
        Bullet bullet = new Bullet(
                new GridPoint2(2, 3),
                Direction.RIGHT,
                0.1f,
                25
        );

        assertEquals(
                new GridPoint2(2, 3),
                bullet.coordinates()
        );
        assertEquals(Direction.RIGHT, bullet.direction());
        assertEquals(25, bullet.damage());
    }

    @Test
    void movesInFacingDirectionAfterEnoughTime() {
        Bullet bullet = new Bullet(
                new GridPoint2(2, 3),
                Direction.UP,
                0.2f,
                25
        );

        bullet.live(0.1f);

        assertEquals(
                new GridPoint2(2, 3),
                bullet.coordinates()
        );

        bullet.live(0.1f);

        assertEquals(
                new GridPoint2(2, 4),
                bullet.coordinates()
        );
    }

    @Test
    void rejectsInvalidMovementSpeedAndDamage() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Bullet(
                        new GridPoint2(0, 0),
                        Direction.RIGHT,
                        0f,
                        25
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Bullet(
                        new GridPoint2(0, 0),
                        Direction.RIGHT,
                        0.1f,
                        0
                )
        );
    }

    @Test
    void coordinatesReturnsCopy() {
        Bullet bullet = new Bullet(
                new GridPoint2(2, 3),
                Direction.RIGHT,
                0.1f,
                25
        );

        bullet.coordinates().set(100, 100);

        assertEquals(
                new GridPoint2(2, 3),
                bullet.coordinates()
        );
    }


    @Test
    void tracksMovementProgress() {
        Bullet bullet = new Bullet(
                new GridPoint2(2, 3),
                Direction.RIGHT,
                0.1f,
                25
        );

        assertEquals(0f, bullet.movementProgress(), 0.0001f);

        bullet.live(0.05f);

        assertEquals(0.5f, bullet.movementProgress(), 0.0001f);
        assertEquals(new GridPoint2(2, 3), bullet.coordinates());

        bullet.live(0.05f);

        assertEquals(0f, bullet.movementProgress(), 0.0001f);
        assertEquals(new GridPoint2(3, 3), bullet.coordinates());
    }

}

