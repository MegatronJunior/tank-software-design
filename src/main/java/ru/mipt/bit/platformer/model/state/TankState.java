package ru.mipt.bit.platformer.model.state;

/**
 * Domain state contract for tank movement and shooting rules; not an application port.
 */
public interface TankState {

    boolean matches(int health, int maximumHealth);

    float movementDurationMultiplier();

    boolean allowsShooting();

    default float shootingCooldownMultiplier() {
        return 1f;
    }
}
