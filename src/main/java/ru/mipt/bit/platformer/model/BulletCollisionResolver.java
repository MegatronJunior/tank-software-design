package ru.mipt.bit.platformer.model;

public final class BulletCollisionResolver {

    public void resolve(Bullet bullet, GameLevel level) {
        if (!level.objects().contains(bullet)) {
            return;
        }

        for (GameObject object : level.objects()) {
            if (object == bullet) {
                continue;
            }

            boolean collided = false;

            if (object instanceof Obstacle) {
                collided = ((Obstacle) object)
                        .isHere(bullet.coordinates());
            } else if (object instanceof Bullet) {
                collided = ((Bullet) object)
                        .coordinates()
                        .equals(bullet.coordinates());
            }

            if (!collided) {
                continue;
            }

            if (object instanceof Tank) {
                Tank tank = (Tank) object;
                tank.takeDamage(bullet.damage());

                if (tank.isDestroyed()) {
                    level.remove(tank);
                }
            }

            if (object instanceof Bullet) {
                level.remove(object);
            }

            level.remove(bullet);
            return;
        }
    }
}
