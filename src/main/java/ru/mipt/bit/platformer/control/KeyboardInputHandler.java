package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.command.GameAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntPredicate;

public class KeyboardInputHandler implements TankControlStrategy{

    private final IntPredicate isKeyPressed;
    private final List<Map.Entry<Integer, GameAction>> keyBindings;
    private final Set<Integer> edgeTriggeredKeys;
    private final Set<Integer> pressedLastFrame = new HashSet<>();

    public KeyboardInputHandler(
            IntPredicate isKeyPressed,
            List<Map.Entry<Integer, GameAction>> keyBindings
    ) {
        this(isKeyPressed, keyBindings, Set.of());
    }

    public KeyboardInputHandler(
            IntPredicate isKeyPressed,
            List<Map.Entry<Integer, GameAction>> keyBindings,
            Set<Integer> edgeTriggeredKeys
    ) {
        this.isKeyPressed = isKeyPressed;
        this.keyBindings = List.copyOf(keyBindings);
        this.edgeTriggeredKeys = Set.copyOf(edgeTriggeredKeys);
    }

    public List<GameAction> readActions() {
        Set<GameAction> actions = new LinkedHashSet<>();
        Set<Integer> pressedThisFrame = new HashSet<>();

        for (Map.Entry<Integer, GameAction> binding : keyBindings) {
            int key = binding.getKey();

            if (isKeyPressed.test(key)) {
                pressedThisFrame.add(key);
                if (!edgeTriggeredKeys.contains(key)
                        || !pressedLastFrame.contains(key)) {
                    actions.add(binding.getValue());
                }
            }
        }

        pressedLastFrame.clear();
        pressedLastFrame.addAll(pressedThisFrame);

        return new ArrayList<>(actions);
    }

    @Override
    public List<GameAction> selectActions() {
        return readActions();
    }
}