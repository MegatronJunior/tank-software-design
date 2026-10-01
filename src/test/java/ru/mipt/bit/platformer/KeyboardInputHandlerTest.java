package ru.mipt.bit.platformer;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class KeyboardInputHandlerTest {
    private Map<Integer, GameAction> keyBindings() {
        return Map.of(
                W, GameAction.MOVE_UP,
                D, GameAction.MOVE_RIGHT
        );
    }

    @Test
    public void wProducesMoveUpAction() {
        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(
                        key -> key == W,
                        keyBindings()
                );

        Set<GameAction> actions = inputHandler.readActions();

        assertTrue(actions.contains(GameAction.MOVE_UP));
    }

    @Test
    public void noPressedKeysProduceNoActions() {
        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(
                        key -> false,
                        keyBindings()
                );

        Set<GameAction> actions = inputHandler.readActions();

        assertTrue(actions.isEmpty());
    }

    @Test
    public void multiplePressedKeysProduceMultipleActions() {
        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(
                        key -> key == W || key == D,
                        keyBindings()
                );

        Set<GameAction> actions = inputHandler.readActions();

        assertTrue(actions.contains(GameAction.MOVE_UP));
        assertTrue(actions.contains(GameAction.MOVE_RIGHT));
        assertEquals(2, actions.size());
    }

    @Test
    public void customKeyBindingProducesConfiguredAction() {
        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(
                        key -> key == SPACE,
                        Map.of(
                                SPACE, GameAction.MOVE_UP
                        )
                );

        Set<GameAction> actions = inputHandler.readActions();

        assertTrue(actions.contains(GameAction.MOVE_UP));
    }
}