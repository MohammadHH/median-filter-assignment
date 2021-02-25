package org.exalt.configurations;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfigurationTest {
    private static String[] nonExistingInput;
    private static String[] textInput;
    private static String[] imageInput;
    private Configuration configuration;

    @BeforeAll
    static void setUp() {
        nonExistingInput = new String[]{"input", "output", "50"};
        textInput = new String[]{ConfigurationTest.class.getClassLoader().getResource("emptyFile.txt").getPath(), "output.txt", "15"};
        imageInput = new String[]{ConfigurationTest.class.getClassLoader().getResource("emptyFile.png").getPath(), "output.png", "15"};
    }

    @Test
    void givenNonExistingFileAnExceptionShouldBeThrown() {
        assertThrows(FileNotFoundException.class, () -> {
            configuration = new Configuration(nonExistingInput);
        });
    }

    @Test
    void givenTextFileInputTypeShouldBeText() throws IOException {
        configuration = new Configuration(textInput);
        assertEquals(FileType.TEXT_FILE, configuration.getInputType());
    }

    @Test
    void givenImageFileInputTypeShouldBeImage() throws IOException {
        configuration = new Configuration(imageInput);
        assertEquals(FileType.IMAGE_FILE, configuration.getInputType());
    }
}