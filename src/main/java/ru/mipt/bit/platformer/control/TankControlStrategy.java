package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.comand.GameAction;

import java.util.List;

public interface TankControlStrategy {

    List<GameAction> selectActions();
}
