package ru.mipt.bit.platformer.model.state;

/**
 * Domain state for a tank with 15 to 70 percent health.
 */
public final class MediumTankState implements TankState {

    @Override
    public boolean matches(int health, int maximumHealth) {
        long healthPercentage = 100L * health;
        return healthPercentage >= 15L * maximumHealth
                && healthPercentage <= 70L * maximumHealth;
    }

    @Override
    public float movementDurationMultiplier() {
        return 2f;
    }

    @Override
    public boolean allowsShooting() {
        return true;
    }
}
