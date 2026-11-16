package ru.mipt.bit.platformer.command;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class ToggleHealthBarsActionTest {

    @Test
    void togglesVisibilityOnEveryExecution() {
        AtomicBoolean visible = new AtomicBoolean(false);
        GameAction action = new ToggleHealthBarsAction(
                () -> visible.set(!visible.get())
        );

        action.execute();
        assertTrue(visible.get());

        action.execute();
        assertFalse(visible.get());
    }

    @Test
    void rejectsNullToggle() {
        assertThrows(
                NullPointerException.class,
                () -> new ToggleHealthBarsAction(null)
        );
    }
}
