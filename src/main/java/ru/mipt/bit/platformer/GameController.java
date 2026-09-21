package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Set;

public class GameController {

    private final Tank tank;
    private final Field field;

    public GameController(Tank tank, Field field) {
        this.tank = tank;
        this.field = field;
    }

    public void update(Set<GameAction> actions, float deltaTime) {
        Direction direction = movementDirection(actions);

        if (direction != null && tank.hasFinishedCurrentMovement()) {
            GridPoint2 nextCoordinates =
                    tank.currentCoordinates().add(direction.getVector());

            if (field.isFree(nextCoordinates)) {
                tank.startMovementTo(nextCoordinates);
            }

            tank.face(direction);
        }

        tank.continueMovement(deltaTime);
    }

    private Direction movementDirection(Set<GameAction> actions) {
        if (actions.contains(GameAction.MOVE_UP)) {
            return Direction.UP;
        } else if (actions.contains(GameAction.MOVE_LEFT)) {
            return Direction.LEFT;
        } else if (actions.contains(GameAction.MOVE_DOWN)) {
            return Direction.DOWN;
        } else if (actions.contains(GameAction.MOVE_RIGHT)) {
            return Direction.RIGHT;
        }

        return null;
    }
}