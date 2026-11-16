package ru.mipt.bit.platformer.model.state;

import java.util.List;

/**
 * Domain service that selects a tank state according to its current health.
 */
public final class TankStateResolver {

    private final List<TankState> states;

    public TankStateResolver() {
        this(List.of(
                new LightTankState(),
                new MediumTankState(),
                new HeavyTankState()
        ));
    }

    public TankStateResolver(List<TankState> states) {
        this.states = List.copyOf(states);
        if (this.states.isEmpty()) {
            throw new IllegalArgumentException("At least one tank state is required");
        }
    }

    public TankState resolve(int health, int maximumHealth) {
        if (maximumHealth <= 0 || health < 0 || health > maximumHealth) {
            throw new IllegalArgumentException("Invalid tank health");
        }

        for (TankState state : states) {
            if (state.matches(health, maximumHealth)) {
                return state;
            }
        }

        throw new IllegalStateException("No tank state matches the current health");
    }
}
