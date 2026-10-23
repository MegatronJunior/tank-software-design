
package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomTankControlStrategyTest {

    @Test
    void selectsMovementActionForRandomDirection() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        Map<Direction, GameAction> actions =
                new EnumMap<>(Direction.class);

        for (Direction direction : Direction.values()) {
            actions.put(direction, () -> {});
        }

        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        RandomTankControlStrategy strategy =
                new RandomTankControlStrategy(
                        tank,
                        random,
                        actions
                );

        List<GameAction> selectedActions =
                strategy.selectActions();

        assertEquals(1, selectedActions.size());
        assertSame(
                actions.get(Direction.values()[0]),
                selectedActions.get(0)
        );
    }

    @Test
    void doesNotSelectActionWhileTankIsMoving() {
        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f
        );

        tank.startMovement(Direction.RIGHT);

        Map<Direction, GameAction> actions =
                new EnumMap<>(Direction.class);

        for (Direction direction : Direction.values()) {
            actions.put(direction, () -> {});
        }

        RandomTankControlStrategy strategy =
                new RandomTankControlStrategy(
                        tank,
                        new Random(42),
                        actions
                );

        assertTrue(strategy.selectActions().isEmpty());
    }
}
