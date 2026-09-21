package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;

import java.util.EnumSet;
import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;

public class KeyboardInputHandler {

    public Set<GameAction> readActions() {
        Set<GameAction> actions = EnumSet.noneOf(GameAction.class);

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            actions.add(GameAction.MOVE_UP);
        }

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            actions.add(GameAction.MOVE_DOWN);
        }

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            actions.add(GameAction.MOVE_LEFT);
        }

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            actions.add(GameAction.MOVE_RIGHT);
        }

        return actions;
    }
}