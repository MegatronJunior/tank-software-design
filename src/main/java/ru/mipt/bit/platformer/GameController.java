package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.comand.GameAction;
import ru.mipt.bit.platformer.control.TankControlStrategy;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final List<Tank> tanks;
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
        this.tanks = List.copyOf(tanks);
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

        for (Tank tank : tanks) {
            tank.continueMovement(deltaTime);
        }
    }
}
