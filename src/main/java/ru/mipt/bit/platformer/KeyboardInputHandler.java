package ru.mipt.bit.platformer;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.IntPredicate;

import static com.badlogic.gdx.Input.Keys.*;

public class KeyboardInputHandler {

    private final IntPredicate isKeyPressed;

    public KeyboardInputHandler(IntPredicate isKeyPressed) {
        this.isKeyPressed = isKeyPressed;
    }

    public Set<GameAction> readActions() {
        Set<GameAction> actions = EnumSet.noneOf(GameAction.class);

        if (isKeyPressed.test(UP) || isKeyPressed.test(W)) {
            actions.add(GameAction.MOVE_UP);
        }

        if (isKeyPressed.test(DOWN) || isKeyPressed.test(S)) {
            actions.add(GameAction.MOVE_DOWN);
        }

        if (isKeyPressed.test(LEFT) || isKeyPressed.test(A)) {
            actions.add(GameAction.MOVE_LEFT);
        }

        if (isKeyPressed.test(RIGHT) || isKeyPressed.test(D)) {
            actions.add(GameAction.MOVE_RIGHT);
        }

        return actions;
    }
}