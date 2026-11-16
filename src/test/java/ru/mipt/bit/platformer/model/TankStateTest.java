package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.command.ShootAction;
import ru.mipt.bit.platformer.model.state.HeavyTankState;
import ru.mipt.bit.platformer.model.state.LightTankState;
import ru.mipt.bit.platformer.model.state.MediumTankState;
import ru.mipt.bit.platformer.model.state.TankState;
import ru.mipt.bit.platformer.model.state.TankStateResolver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TankStateTest {

    private Tank newTank(int maximumHealth) {
        return new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f,
                maximumHealth
        );
    }

    @Test
    void changesStateAtExactHealthThresholds() {
        Tank tank = newTank(100);

        assertInstanceOf(LightTankState.class, tank.state());
        tank.takeDamage(29);
        assertInstanceOf(LightTankState.class, tank.state());

        tank.takeDamage(1);
        assertInstanceOf(MediumTankState.class, tank.state());

        tank.takeDamage(55);
        assertInstanceOf(MediumTankState.class, tank.state());

        tank.takeDamage(1);
        assertInstanceOf(HeavyTankState.class, tank.state());
    }

    @Test
    void usesPercentageOfInitialHealthForStateTransitions() {
        Tank tank = newTank(200);

        tank.takeDamage(59);
        assertInstanceOf(LightTankState.class, tank.state());

        tank.takeDamage(1);
        assertInstanceOf(MediumTankState.class, tank.state());

        tank.takeDamage(110);
        assertInstanceOf(MediumTankState.class, tank.state());

        tank.takeDamage(1);
        assertInstanceOf(HeavyTankState.class, tank.state());
    }

    @Test
    void changesMovementDurationAccordingToState() {
        Tank lightTank = newTank(100);
        lightTank.startMovement(Direction.RIGHT);
        lightTank.live(0.4f);
        assertTrue(lightTank.hasFinishedCurrentMovement());

        Tank mediumTank = newTank(100);
        mediumTank.takeDamage(30);
        mediumTank.startMovement(Direction.RIGHT);
        mediumTank.live(0.4f);
        assertEquals(0.5f, mediumTank.movementProgress(), 0.0001f);
        mediumTank.live(0.4f);
        assertTrue(mediumTank.hasFinishedCurrentMovement());

        Tank heavyTank = newTank(100);
        heavyTank.takeDamage(86);
        heavyTank.startMovement(Direction.RIGHT);
        heavyTank.live(0.4f);
        assertEquals(1f / 3f, heavyTank.movementProgress(), 0.0001f);
        heavyTank.live(0.8f);
        assertTrue(heavyTank.hasFinishedCurrentMovement());
    }

    @Test
    void damageAffectsMovementAlreadyInProgress() {
        Tank tank = newTank(100);
        tank.startMovement(Direction.RIGHT);
        tank.live(0.1f);
        tank.takeDamage(30);
        tank.live(0.2f);

        assertEquals(0.5f, tank.movementProgress(), 0.0001f);
    }

    @Test
    void mediumTankCanStillShootAtFifteenPercentHealth() {
        Tank tank = newTank(100);
        tank.takeDamage(85);

        assertInstanceOf(MediumTankState.class, tank.state());
        assertTrue(tank.canShoot());
        assertDoesNotThrow(() -> tank.shoot(0.1f, 25));
    }

    @Test
    void heavyTankCannotShootEvenAfterReloading() {
        Tank tank = newTank(100);
        GameLevel level = new GameLevel();
        level.add(tank);
        ShootAction shootAction = new ShootAction(tank, level, 0.1f, 25);

        tank.takeDamage(86);
        assertFalse(tank.canShoot());
        assertThrows(IllegalStateException.class, () -> tank.shoot(0.1f, 25));

        shootAction.execute();
        assertEquals(List.of(tank), level.objects());
    }

    @Test
    void additionalStatesCanBeInjectedWithoutChangingTank() {
        TankState boostedState = new TankState() {
            @Override
            public boolean matches(int health, int maximumHealth) {
                return 100L * health > 70L * maximumHealth;
            }

            @Override
            public float movementDurationMultiplier() {
                return 0.5f;
            }

            @Override
            public boolean allowsShooting() {
                return true;
            }

            @Override
            public float shootingCooldownMultiplier() {
                return 0.5f;
            }
        };

        TankStateResolver resolver = new TankStateResolver(List.of(
                boostedState,
                new LightTankState(),
                new MediumTankState(),
                new HeavyTankState()
        ));

        Tank tank = new Tank(
                new GridPoint2(1, 1),
                Direction.RIGHT,
                0.4f,
                100,
                resolver
        );

        tank.startMovement(Direction.RIGHT);
        tank.live(0.2f);
        assertTrue(tank.hasFinishedCurrentMovement());

        tank.shoot(0.1f, 25);
        tank.live(0.25f);
        assertTrue(tank.canShoot());

        tank.takeDamage(30);
        assertInstanceOf(MediumTankState.class, tank.state());
    }
}
