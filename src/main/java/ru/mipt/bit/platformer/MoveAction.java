package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

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