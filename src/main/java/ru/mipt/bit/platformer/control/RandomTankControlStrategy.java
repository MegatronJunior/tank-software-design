package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.command.GameAction;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Objects;


public final class RandomTankControlStrategy
        implements TankControlStrategy {

    private final Tank tank;
    private final Random random;
    private final Map<Direction, GameAction> movementActions;
    private final GameAction shootAction;

    public RandomTankControlStrategy(
            Tank tank,
            Random random,
            Map<Direction, GameAction> movementActions,
            GameAction shootAction
    ) {
        this.tank = Objects.requireNonNull(tank);
        this.random = Objects.requireNonNull(random);
        this.movementActions = Map.copyOf(movementActions);
        this.shootAction = Objects.requireNonNull(shootAction);

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
        if (tank.isDestroyed()) {
            return List.of();
        }

        if (!tank.hasFinishedCurrentMovement()) {
            return List.of();
        }

        if (tank.canShoot() && random.nextInt(5) == 0) {
            return List.of(shootAction);
        }

        Direction[] directions = Direction.values();
        Direction direction =
                directions[random.nextInt(directions.length)];

        return List.of(movementActions.get(direction));
    }
}

