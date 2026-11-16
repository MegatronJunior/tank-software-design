package ru.mipt.bit.platformer.command;

import java.util.Objects;

/**
 * Application command for changing a presentation setting.
 * A named display-settings port would separate this use case from the UI.
 */
public final class ToggleHealthBarsAction implements GameAction {

    private final Runnable toggleVisibility;

    public ToggleHealthBarsAction(Runnable toggleVisibility) {
        this.toggleVisibility = Objects.requireNonNull(toggleVisibility);
    }

    @Override
    public void execute() {
        toggleVisibility.run();
    }
}
