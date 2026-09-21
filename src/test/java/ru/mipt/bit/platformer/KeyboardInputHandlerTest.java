package ru.mipt.bit.platformer;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;
import static org.junit.jupiter.api.Assertions.*;

public class KeyboardInputHandlerTest {

    @Test
    public void wProducesMoveUpAction() {
        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(key -> key == W);

        Set<GameAction> actions = inputHandler.readActions();

        assertTrue(actions.contains(GameAction.MOVE_UP));
    }

    @Test
    public void noPressedKeysProduceNoActions() {
        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(key -> false);

        Set<GameAction> actions = inputHandler.readActions();

        assertTrue(actions.isEmpty());
    }

    @Test
    public void multiplePressedKeysProduceMultipleActions() {
        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(
                        key -> key == W || key == D
                );

        Set<GameAction> actions = inputHandler.readActions();

        assertTrue(actions.contains(GameAction.MOVE_UP));
        assertTrue(actions.contains(GameAction.MOVE_RIGHT));
        assertEquals(2, actions.size());
    }
}