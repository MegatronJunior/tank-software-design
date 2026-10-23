package ru.mipt.bit.platformer;

import java.util.List;

public interface TankControlStrategy {

    List<GameAction> selectActions();
}
