package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank {

    private final GridPoint2 coordinates;
    private final GridPoint2 destinationCoordinates;

    private float movementProgress;
    private Direction direction;

    private final float movementSpeed;

    public Tank( GridPoint2 initialCoordinates,
                 Direction initialDirection,
                 float movementSpeed) {
        this.coordinates = new GridPoint2(initialCoordinates);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.movementProgress = 1f;
        this.direction = initialDirection;
        this.movementSpeed = movementSpeed;
    }

    public boolean hasFinishedCurrentMovement() {
        return isEqual(movementProgress, 1f);
    }

    public void face(Direction direction) {
        this.direction = direction;
    }

    public void startMovementTo(GridPoint2 destination) {
        this.destinationCoordinates.set(destination);
        this.movementProgress = 0f;
    }

    public GridPoint2 currentCoordinates() {
        return new GridPoint2(coordinates);
    }

    public GridPoint2 destinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
    }

    public float movementProgress() {
        return movementProgress;
    }

    public void continueMovement(float deltaTime) {
        movementProgress =
                continueProgress(
                        movementProgress,
                        deltaTime,
                        movementSpeed
                );

        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destinationCoordinates);
        }
    }

    public float rotationAngle() {
        return direction.getAngle();
    }

}