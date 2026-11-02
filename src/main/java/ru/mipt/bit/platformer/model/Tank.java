package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static com.badlogic.gdx.math.MathUtils.clamp;


public class Tank implements Obstacle {

    private final GridPoint2 coordinates;
    private final GridPoint2 destinationCoordinates;

    private float movementProgress;
    private Direction direction;

    private final float movementSpeed;

    public Tank( GridPoint2 initialCoordinates,
                 Direction initialDirection,
                 float movementSpeed) {
        if (!Float.isFinite(movementSpeed) || movementSpeed <= 0f) {
            throw new IllegalArgumentException(
                    "Movement speed must be positive and finite"
            );
        }
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

    public void startMovement(Direction direction) {
        if (!hasFinishedCurrentMovement()) {
            return;
        }

        destinationCoordinates
                .set(coordinates)
                .add(direction.getVector());

        movementProgress = 0f;
        face(direction);
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
        movementProgress = clamp(
                movementProgress + deltaTime / movementSpeed,
                0f,
                1f
        );

        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destinationCoordinates);
        }
    }

    public float rotationAngle() {
        return direction.getAngle();
    }

    @Override
    public boolean isHere(GridPoint2 coordinates) {
        return this.coordinates.equals(coordinates)
                || destinationCoordinates.equals(coordinates);
    }

}