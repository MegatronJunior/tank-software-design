package ru.mipt.bit.platformer;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;
import static org.junit.jupiter.api.Assertions.*;

public class KeyboardInputHandlerTest {

    private final GameAction moveUp = () -> {};
    private final GameAction moveRight = () -> {};

    private List<Map.Entry<Integer, GameAction>> keyBindings() {
        return List.of(
                Map.entry(W, moveUp),
                Map.entry(D, moveRight)
        );
    }

    @Test
    public void wProducesConfiguredAction() {
        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(
                        key -> key == W,
                        keyBindings()
                );

        List<GameAction> actions = inputHandler.readActions();

        assertEquals(
                List.of(moveUp),
                actions
        );
    }

    @Test
    public void noPressedKeysProduceNoActions() {
        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(
                        key -> false,
                        keyBindings()
                );

        List<GameAction> actions = inputHandler.readActions();

        assertTrue(actions.isEmpty());
    }

    @Test
    public void multiplePressedKeysProduceMultipleActions() {
        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(
                        key -> key == W || key == D,
                        keyBindings()
                );

        List<GameAction> actions = inputHandler.readActions();

        assertEquals(
                List.of(moveUp, moveRight),
                actions
        );
    }

    @Test
    public void customKeyBindingProducesConfiguredAction() {
        GameAction customAction = () -> {};

        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(
                        key -> key == SPACE,
                        List.of(
                                Map.entry(SPACE, customAction)
                        )
                );

        List<GameAction> actions = inputHandler.readActions();

        assertEquals(
                List.of(customAction),
                actions
        );
    }

    @Test
    public void twoKeysForSameActionProduceOneAction() {
        KeyboardInputHandler inputHandler =
                new KeyboardInputHandler(
                        key -> key == W || key == UP,
                        List.of(
                                Map.entry(W, moveUp),
                                Map.entry(UP, moveUp)
                        )
                );

        List<GameAction> actions = inputHandler.readActions();

        assertEquals(
                List.of(moveUp),
                actions
        );
    }
}