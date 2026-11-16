package ru.mipt.bit.platformer.command;

import java.util.Objects;

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
