package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.command.GameAction;
import ru.mipt.bit.platformer.control.TankControlStrategy;
import ru.mipt.bit.platformer.model.GameLevel;
import ru.mipt.bit.platformer.model.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Application service: executes game commands and advances the domain model.
 */
public class GameController {

    private final GameLevel level;
    private final List<TankControlStrategy> controlStrategies;

    public GameController(GameObject object) {
        this(List.of(object));
    }

    public GameController(List<? extends GameObject> objects) {
        this(objects, List.of());
    }

    public GameController(
            List<? extends GameObject> objects,
            List<TankControlStrategy> controlStrategies
    ) {
        this(new GameLevel(), controlStrategies);

        for (GameObject object : objects) {
            level.add(object);
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
