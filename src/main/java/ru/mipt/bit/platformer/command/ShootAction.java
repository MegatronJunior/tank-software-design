package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.model.GameLevel;
import ru.mipt.bit.platformer.model.Tank;

import java.util.Objects;

public final class ShootAction implements GameAction {

    private final Tank tank;
    private final GameLevel level;
    private final float bulletMovementSpeed;
    private final int bulletDamage;

    public ShootAction(
            Tank tank,
            GameLevel level,
            float bulletMovementSpeed,
            int bulletDamage
    ) {
        this.tank = Objects.requireNonNull(tank);
        this.level = Objects.requireNonNull(level);
        this.bulletMovementSpeed = bulletMovementSpeed;
        this.bulletDamage = bulletDamage;
    }


    @Override
    public void execute() {
        if (!level.objects().contains(tank) || !tank.canShoot()) {
            return;
        }

        level.add(tank.shoot(bulletMovementSpeed, bulletDamage));
    }

}
