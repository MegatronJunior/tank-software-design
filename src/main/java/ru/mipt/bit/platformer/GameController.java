package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.command.GameAction;
import ru.mipt.bit.platformer.control.TankControlStrategy;
import ru.mipt.bit.platformer.model.GameLevel;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameController {

    private final GameLevel level;
    private final List<TankControlStrategy> controlStrategies;

    public GameController(Tank tank) {
        this(List.of(tank));
    }

    public GameController(List<Tank> tanks) {
        this(tanks, List.of());
    }

    public GameController(
            List<Tank> tanks,
            List<TankControlStrategy> controlStrategies
    ) {
        this(new GameLevel(), controlStrategies);

        for (Tank tank : tanks) {
            level.add(tank);
        }
    }

    public GameController(
            GameLevel level,
            List<TankControlStrategy> controlStrategies
    ) {
        this.level = Objects.requireNonNull(level);
        this.controlStrategies = List.copyOf(controlStrategies);
    }

    public void update(float deltaTime) {
        List<GameAction> actions = new ArrayList<>();

        for (TankControlStrategy strategy : controlStrategies) {
            actions.addAll(strategy.selectActions());
        }

        update(actions, deltaTime);
    }

    public void update(
            List<GameAction> actions,
            float deltaTime
    ) {
        for (GameAction action : actions) {
            action.execute();
        }

        level.live(deltaTime);
    }
}
