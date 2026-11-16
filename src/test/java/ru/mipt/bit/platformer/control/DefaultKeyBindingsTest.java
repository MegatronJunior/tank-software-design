package ru.mipt.bit.platformer.control;

import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.command.GameAction;
import ru.mipt.bit.platformer.model.Direction;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class DefaultKeyBindingsTest {

    private Map<Direction, GameAction> movementActions() {
        Map<Direction, GameAction> actions = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.values()) {
            actions.put(direction, () -> {});
        }
        return actions;
    }

    private Map<Integer, GameAction> toMap(List<Map.Entry<Integer, GameAction>> bindings) {
        return bindings.stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
        ));
    }

    @Test
    void arrowKeysAndWasdTriggerMatchingDirections() {
        Map<Direction, GameAction> actions = movementActions();
        GameAction shootAction = () -> {};
        Map<Integer, GameAction> bindings = toMap(
                DefaultKeyBindings.create(actions, shootAction)
        );

        assertEquals(9, bindings.size());
        assertSame(actions.get(Direction.UP), bindings.get(Input.Keys.UP));
        assertSame(actions.get(Direction.UP), bindings.get(Input.Keys.W));
        assertSame(actions.get(Direction.DOWN), bindings.get(Input.Keys.DOWN));
        assertSame(actions.get(Direction.DOWN), bindings.get(Input.Keys.S));
        assertSame(actions.get(Direction.LEFT), bindings.get(Input.Keys.LEFT));
        assertSame(actions.get(Direction.LEFT), bindings.get(Input.Keys.A));
        assertSame(actions.get(Direction.RIGHT), bindings.get(Input.Keys.RIGHT));
        assertSame(actions.get(Direction.RIGHT), bindings.get(Input.Keys.D));
        assertSame(shootAction, bindings.get(Input.Keys.SPACE));
    }

    @Test
    void extendedBindingsIncludeHealthBarToggle() {
        Map<Direction, GameAction> actions = movementActions();
        GameAction shootAction = () -> {};
        GameAction toggleHealthBarsAction = () -> {};
        Map<Integer, GameAction> bindings = toMap(
                DefaultKeyBindings.create(
                        actions,
                        shootAction,
                        toggleHealthBarsAction
                )
        );

        assertEquals(10, bindings.size());
        assertSame(shootAction, bindings.get(Input.Keys.SPACE));
        assertSame(toggleHealthBarsAction, bindings.get(Input.Keys.L));
    }
}
