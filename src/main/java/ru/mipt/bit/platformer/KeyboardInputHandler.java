package ru.mipt.bit.platformer;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.Map;

public class KeyboardInputHandler {

    private final IntPredicate isKeyPressed;
    private final Map<Integer, GameAction> keyBindings;

    public KeyboardInputHandler(
            IntPredicate isKeyPressed,
            Map<Integer, GameAction> keyBindings
    ) {
        this.isKeyPressed = isKeyPressed;
        this.keyBindings = Map.copyOf(keyBindings);
    }

    public Set<GameAction> readActions() {
        Set<GameAction> actions = EnumSet.noneOf(GameAction.class);

        for (Map.Entry<Integer, GameAction> binding : keyBindings.entrySet()) {
            if (isKeyPressed.test(binding.getKey())) {
                actions.add(binding.getValue());
            }
        }

        return actions;
    }
}