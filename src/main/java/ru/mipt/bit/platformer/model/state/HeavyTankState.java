package ru.mipt.bit.platformer.model.state;

/**
 * Domain state for a tank with less than 15 percent health.
 */
public final class HeavyTankState implements TankState {

    @Override
    public boolean matches(int health, int maximumHealth) {
        return 100L * health < 15L * maximumHealth;
    }

    @Override
    public float movementDurationMultiplier() {
        return 3f;
    }

    @Override
    public boolean allowsShooting() {
        return false;
    }
}
