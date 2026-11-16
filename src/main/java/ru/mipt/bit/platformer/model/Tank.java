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

    private static final int DEFAULT_HEALTH = 100;

    private final int maxHealth;
    private int health;

    private static final float SHOOT_COOLDOWN_SECONDS = 0.5f;

    private float reloadTimeRemaining;


    public Tank(
            GridPoint2 initialCoordinates,
            Direction initialDirection,
            float movementSpeed
    ) {
        this(
                initialCoordinates,
                initialDirection,
                movementSpeed,
                DEFAULT_HEALTH
        );
    }

    public Tank(
            GridPoint2 initialCoordinates,
            Direction initialDirection,
            float movementSpeed,
            int initialHealth
    ) {
        if (!Float.isFinite(movementSpeed) || movementSpeed <= 0f) {
            throw new IllegalArgumentException(
                    "Movement speed must be positive and finite"
            );
        }

        if (initialHealth <= 0) {
            throw new IllegalArgumentException(
                    "Initial health must be positive"
            );
        }

        this.coordinates = new GridPoint2(initialCoordinates);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.movementProgress = 1f;
        this.direction = initialDirection;
        this.movementSpeed = movementSpeed;
        this.maxHealth = initialHealth;
        this.health = initialHealth;
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

    public boolean canShoot() {
        return !isDestroyed()
                && hasFinishedCurrentMovement()
                && reloadTimeRemaining <= 0f;
    }

    public Bullet shoot(float bulletMovementSpeed, int damage) {
        if (!canShoot()) {
            throw new IllegalStateException("Tank cannot shoot now");
        }

        GridPoint2 bulletCoordinates = currentCoordinates()
                .add(direction.getVector());

        Bullet bullet = new Bullet(
                bulletCoordinates,
                direction,
                bulletMovementSpeed,
                damage,
                this
        );

        reloadTimeRemaining = SHOOT_COOLDOWN_SECONDS;

        return bullet;
    }



    public int health() {
        return health;
    }

    public float healthFraction() {
        return (float) health / maxHealth;
    }

    public boolean isDestroyed() {
        return health == 0;
    }

    public void takeDamage(int damage) {
        if (damage <= 0) {
            throw new IllegalArgumentException(
                    "Damage must be positive"
            );
        }

        health = Math.max(0, health - damage);
    }


    public float rotationAngle() {
        return direction.getAngle();
    }

    @Override
    public boolean isHere(GridPoint2 coordinates) {
        return this.coordinates.equals(coordinates)
                || destinationCoordinates.equals(coordinates);
    }

    @Override
    public void live(float deltaTime) {
        continueMovement(deltaTime);
        reloadTimeRemaining = Math.max(
                0f,
                reloadTimeRemaining - deltaTime
        );
    }
}