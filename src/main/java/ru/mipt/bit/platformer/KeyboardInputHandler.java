package ru.mipt.bit.platformer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntPredicate;

public class KeyboardInputHandler {

    private final IntPredicate isKeyPressed;
    private final List<Map.Entry<Integer, GameAction>> keyBindings;

    public KeyboardInputHandler(
            IntPredicate isKeyPressed,
            List<Map.Entry<Integer, GameAction>> keyBindings
    ) {
        this.isKeyPressed = isKeyPressed;
        this.keyBindings = List.copyOf(keyBindings);
    }

    public List<GameAction> readActions() {
        Set<GameAction> actions = new LinkedHashSet<>();

        for (Map.Entry<Integer, GameAction> binding : keyBindings) {
            if (isKeyPressed.test(binding.getKey())) {
                actions.add(binding.getValue());
            }
        }

        return new ArrayList<>(actions);
    }
}