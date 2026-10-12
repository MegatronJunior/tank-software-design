package ru.mipt.bit.platformer;

import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;

public final class DefaultKeyBindings {

    private DefaultKeyBindings() {
    }

    public static List<Map.Entry<Integer, GameAction>> create(
            Map<Direction, GameAction> movementActions
    ) {
        return List.of(
                Map.entry(UP, movementActions.get(Direction.UP)),
                Map.entry(W, movementActions.get(Direction.UP)),

                Map.entry(LEFT, movementActions.get(Direction.LEFT)),
                Map.entry(A, movementActions.get(Direction.LEFT)),

                Map.entry(DOWN, movementActions.get(Direction.DOWN)),
                Map.entry(S, movementActions.get(Direction.DOWN)),

                Map.entry(RIGHT, movementActions.get(Direction.RIGHT)),
                Map.entry(D, movementActions.get(Direction.RIGHT))
        );
    }
}