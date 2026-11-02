package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Objects;

public final class Bullet implements GameObject {

    private final GridPoint2 coordinates;
    private final Direction direction;
    private final float movementSpeed;
    private final int damage;

    private float elapsedTime;

    public Bullet(
            GridPoint2 coordinates,
            Direction direction,
            float movementSpeed,
            int damage
    ) {
        if (!Float.isFinite(movementSpeed) || movementSpeed <= 0f) {
            throw new IllegalArgumentException(
                    "Movement speed must be positive and finite"
            );
        }

        if (damage <= 0) {
            throw new IllegalArgumentException(
                    "Damage must be positive"
            );
        }

        this.coordinates = new GridPoint2(
                Objects.requireNonNull(coordinates)
        );
        this.direction = Objects.requireNonNull(direction);
        this.movementSpeed = movementSpeed;
        this.damage = damage;
    }

    public GridPoint2 coordinates() {
        return new GridPoint2(coordinates);
    }

    public Direction direction() {
        return direction;
    }

    public float movementProgress() {
        return Math.min(1f, elapsedTime / movementSpeed);
    }

    public int damage() {
        return damage;
    }

    @Override
    public void live(float deltaTime) {
        if (!Float.isFinite(deltaTime) || deltaTime < 0f) {
            throw new IllegalArgumentException(
                    "Delta time must be non-negative and finite"
            );
        }

        elapsedTime += deltaTime;

        if (elapsedTime >= movementSpeed) {
            coordinates.add(direction.getVector());
            elapsedTime -= movementSpeed;
        }
    }
}

