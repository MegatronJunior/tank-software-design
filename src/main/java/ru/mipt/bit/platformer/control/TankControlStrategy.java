package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.command.GameAction;

import java.util.List;

/**
 * Inbound application port through which a control source supplies game commands.
 */
public interface TankControlStrategy {

    List<GameAction> selectActions();
}
