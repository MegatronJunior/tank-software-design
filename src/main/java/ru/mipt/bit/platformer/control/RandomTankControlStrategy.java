
package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.comand.GameAction;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public final class RandomTankControlStrategy
        implements TankControlStrategy {

    private final Tank tank;
    private final Random random;
    private final Map<Direction, GameAction> movementActions;

    public RandomTankControlStrategy(
            Tank tank,
            Random random,
            Map<Direction, GameAction> movementActions
    ) {
        this.tank = Objects.requireNonNull(tank);
        this.random = Objects.requireNonNull(random);
        this.movementActions = Map.copyOf(movementActions);

        if (!this.movementActions.keySet().containsAll(
                EnumSet.allOf(Direction.class)
        )) {
            throw new IllegalArgumentException(
                    "Movement actions must cover all directions"
            );
        }
    }

    @Override
    public List<GameAction> selectActions() {
        if (!tank.hasFinishedCurrentMovement()) {
            return List.of();
        }

        Direction[] directions = Direction.values();
        Direction direction =
                directions[random.nextInt(directions.length)];

        return List.of(movementActions.get(direction));
    }
}

