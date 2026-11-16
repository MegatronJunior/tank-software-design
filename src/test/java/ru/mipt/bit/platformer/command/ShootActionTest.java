package ru.mipt.bit.platformer.command;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameLevel;
import ru.mipt.bit.platformer.model.Tank;

import static org.junit.jupiter.api.Assertions.*;

class ShootActionTest {

    @Test
    void createsBulletAndRegistersItInLevel() {
        GameLevel level = new GameLevel();

        Tank tank = new Tank(
                new GridPoint2(2, 3),
                Direction.RIGHT,
                0.4f
        );

        level.add(tank);

        ShootAction action = new ShootAction(
                tank,
                level,
                0.1f,
                25
        );

        action.execute();

        assertEquals(2, level.objects().size());

        Bullet bullet = (Bullet) level.objects().stream()
                .filter(Bullet.class::isInstance)
                .findFirst()
                .orElseThrow();

        assertEquals(
                new GridPoint2(3, 3),
                bullet.coordinates()
        );
        assertEquals(Direction.RIGHT, bullet.direction());
        assertEquals(25, bullet.damage());
    }

    @Test
    void doesNotShootWhileMoving() {
        GameLevel level = new GameLevel();

        Tank tank = new Tank(
                new GridPoint2(2, 3),
                Direction.RIGHT,
                0.4f
        );

        level.add(tank);
        tank.startMovement(Direction.RIGHT);

        ShootAction action = new ShootAction(
                tank,
                level,
                0.1f,
                25
        );

        action.execute();

        assertEquals(1, level.objects().size());
    }


    @Test
    void tankMustReloadBetweenShots() {
        GameLevel level = new GameLevel();

        Tank tank = new Tank(
                new GridPoint2(2, 3),
                Direction.RIGHT,
                0.4f
        );

        level.add(tank);

        ShootAction action = new ShootAction(
                tank,
                level,
                0.1f,
                25
        );

        action.execute();
        action.execute();

        assertEquals(
                1,
                level.objects().stream()
                        .filter(Bullet.class::isInstance)
                        .count()
        );

        level.live(0.25f);
        action.execute();

        assertEquals(
                1,
                level.objects().stream()
                        .filter(Bullet.class::isInstance)
                        .count()
        );

        level.live(0.25f);
        action.execute();

        assertEquals(
                2,
                level.objects().stream()
                        .filter(Bullet.class::isInstance)
                        .count()
        );
    }
}
