package ru.mipt.bit.platformer.model.state;

/**
 * Domain state for a tank with more than 70 percent health.
 */
public final class LightTankState implements TankState {

    @Override
    public boolean matches(int health, int maximumHealth) {
        return 100L * health > 70L * maximumHealth;
    }

    @Override
    public float movementDurationMultiplier() {
        return 1f;
    }

    @Override
    public boolean allowsShooting() {
        return true;
    }
}
