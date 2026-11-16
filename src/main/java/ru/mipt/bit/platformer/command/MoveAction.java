package ru.mipt.bit.platformer.command;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Field;
import ru.mipt.bit.platformer.model.Tank;

/**
 * Application use case for tank movement.
 * It currently depends directly on Field; a cell-occupancy port is missing.
 */
public class MoveAction implements GameAction {

    private final Tank tank;
    private final Field field;
    private final Direction direction;

    public MoveAction(
            Tank tank,
            Field field,
            Direction direction
    ) {
        this.tank = tank;
        this.field = field;
        this.direction = direction;
    }

    @Override
    public void execute() {
        if (tank.isDestroyed()) {
            return;
        }

        if (!tank.hasFinishedCurrentMovement()) {
            return;
        }

        GridPoint2 nextCoordinates =
                tank.currentCoordinates().add(direction.getVector());

        if (field.isFree(nextCoordinates)) {
            tank.startMovement(direction);
        } else {
            tank.face(direction);
        }
    }
}