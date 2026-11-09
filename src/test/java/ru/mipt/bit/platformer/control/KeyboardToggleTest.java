package ru.mipt.bit.platformer.control;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.command.GameAction;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.badlogic.gdx.Input.Keys.L;
import static com.badlogic.gdx.Input.Keys.W;
import static org.junit.jupiter.api.Assertions.*;

public class KeyboardToggleTest {

    @Test
    void toggleFiresOncePerPress() {
        Set<Integer> pressed = new HashSet<>();
        GameAction toggle = () -> {};
        KeyboardInputHandler handler = new KeyboardInputHandler(
                pressed::contains,
                List.of(Map.entry(L, toggle)),
                Set.of(L)
        );

        assertTrue(handler.readActions().isEmpty());
        pressed.add(L);
        assertEquals(List.of(toggle), handler.readActions());
        assertTrue(handler.readActions().isEmpty());
        pressed.remove(L);
        assertTrue(handler.readActions().isEmpty());
        pressed.add(L);
        assertEquals(List.of(toggle), handler.readActions());
    }

    @Test
    void heldMovementKeyStillRepeats() {
        GameAction move = () -> {};
        KeyboardInputHandler handler = new KeyboardInputHandler(
                key -> key == W,
                List.of(Map.entry(W, move)),
                Set.of(L)
        );

        assertEquals(List.of(move), handler.readActions());
        assertEquals(List.of(move), handler.readActions());
    }
}
