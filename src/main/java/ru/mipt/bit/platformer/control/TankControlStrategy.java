package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.command.GameAction;

import java.util.List;

public interface TankControlStrategy {

    List<GameAction> selectActions();
}
