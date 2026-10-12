package ru.mipt.bit.platformer;

import java.util.List;

public class GameController {

    private final Tank tank;

    public GameController(Tank tank) {
        this.tank = tank;
    }

    public void update(List<GameAction> actions, float deltaTime) {
        for (GameAction action : actions) {
            action.execute();
        }

        tank.continueMovement(deltaTime);
    }
}