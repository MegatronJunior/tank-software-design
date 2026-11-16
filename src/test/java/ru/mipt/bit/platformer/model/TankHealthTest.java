package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TankHealthTest {

    @Test
    void healthFractionReflectsDamage() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f,
                200
        );

        assertEquals(1f, tank.healthFraction());
        tank.takeDamage(50);
        assertEquals(0.75f, tank.healthFraction());
        tank.takeDamage(200);
        assertEquals(0f, tank.healthFraction());
    }

    @Test
    void rejectsInvalidInitialHealth() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Tank(new GridPoint2(1, 1), Direction.RIGHT, 0.4f, 0)
        );
    }
}
