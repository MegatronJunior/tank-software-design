
package ru.mipt.bit.platformer.model;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class GameLevel {

    private final Set<GameObject> objects = new LinkedHashSet<>();
    private final Set<GameLevelListener> listeners = new LinkedHashSet<>();
    private final BulletCollisionResolver collisionResolver = new BulletCollisionResolver();

    private final Field field;

    public GameLevel() {
        this.field = null;
    }

    public GameLevel(Field field) {
        this.field = Objects.requireNonNull(field);
    }

    private void resolveBullet(Bullet bullet) {
        if (field != null && !field.isInside(bullet.coordinates())) {
            remove(bullet);
            return;
        }

        collisionResolver.resolve(bullet, this);
    }

    public boolean add(GameObject object) {
        if (!objects.add(Objects.requireNonNull(object))) {
            return false;
        }

        for (GameLevelListener listener : List.copyOf(listeners)) {
            listener.onObjectAdded(object);
        }

        if (object instanceof Bullet) {
            resolveBullet((Bullet) object);
        }
        return true;
    }

    public void addAll(Collection<? extends GameObject> newObjects) {
        for (GameObject object : newObjects) {
            add(object);
        }
    }

    public boolean remove(GameObject object) {
        if (!objects.remove(object)) {
            return false;
        }

        for (GameLevelListener listener : List.copyOf(listeners)) {
            listener.onObjectRemoved(object);
        }

        return true;
    }

    public List<GameObject> objects() {
        return List.copyOf(objects);
    }

    public void addListener(GameLevelListener listener) {
        Objects.requireNonNull(listener);

        if (listeners.add(listener)) {
            for (GameObject object : objects()) {
                listener.onObjectAdded(object);
            }
        }
    }

    public void removeListener(GameLevelListener listener) {
        listeners.remove(listener);
    }


    public void live(float deltaTime) {
        for (GameObject object : objects()) {
            if (!objects.contains(object)) {
                continue;
            }

            object.live(deltaTime);

            if (object instanceof Bullet) {
                resolveBullet((Bullet) object);
            }
        }
    }

}
