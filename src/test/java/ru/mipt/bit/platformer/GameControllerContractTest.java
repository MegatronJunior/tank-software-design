package ru.mipt.bit.platformer;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.GameObject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameControllerContractTest {

    @Test
    void updatesGameObjectsThatAreNotTanks() {
        int[] updateCount = {0};
        GameObject object = new GameObject() {
            @Override
            public void live(float deltaTime) {
                updateCount[0]++;
            }
        };

        GameController controller = new GameController(List.of(object));
        controller.update(0.1f);

        assertEquals(1, updateCount[0]);
    }
}
