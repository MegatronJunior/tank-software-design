
package ru.mipt.bit.platformer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileLevelProviderTest {

    @TempDir
    Path tempDir;

    private FileLevelProvider providerFor(String content)
            throws IOException {

        Path path = tempDir.resolve("level.txt");
        Files.writeString(path, content, StandardCharsets.UTF_8);

        return new FileLevelProvider(
                new FileHandle(path.toFile())
        );
    }

    @Test
    public void readsLevelCorrectly() throws IOException {
        String content = String.join("\n",
                "___T__T___",
                "__TT__TTTT",
                "_________T",
                "TTTT__T__T",
                "_____X____",
                "__________"
        );

        LevelData level = providerFor(content).createLevel(10, 6);

        assertEquals(10, level.width());
        assertEquals(6, level.height());
        assertEquals(new GridPoint2(5, 1), level.tankCoordinates());
        assertEquals(15, level.trees().size());

        assertTrue(level.trees().stream()
                .anyMatch(tree -> tree.isHere(new GridPoint2(3, 5))));
    }

    @Test
    public void rejectsMissingTank() throws IOException {
        FileLevelProvider provider = providerFor(
                "___\n_T_"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> provider.createLevel(3, 2)
        );
    }

    @Test
    public void rejectsMultipleTanks() throws IOException {
        FileLevelProvider provider = providerFor(
                "X_X\n___"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> provider.createLevel(3, 2)
        );
    }

    @Test
    public void rejectsUnknownSymbols() throws IOException {
        FileLevelProvider provider = providerFor(
                "X?_\n___"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> provider.createLevel(3, 2)
        );
    }

    @Test
    public void rejectsIncorrectDimensions() throws IOException {
        FileLevelProvider provider = providerFor(
                "X__\n_T_"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> provider.createLevel(4, 2)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> provider.createLevel(3, 3)
        );
    }
}
