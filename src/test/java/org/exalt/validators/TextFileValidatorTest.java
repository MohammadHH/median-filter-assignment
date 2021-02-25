package org.exalt.validators;

import org.exalt.exceptions.EntryFormatException;
import org.exalt.exceptions.NonUniformEntriesLengthException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TextFileValidatorTest {
    private static TextFileValidator validator;

    @BeforeAll
    static void setUp() {
        validator = new TextFileValidator();
    }

    @Test
    void givenEmptyFileItShouldHaveZeroRows() throws FileNotFoundException {
        File file = new File(TextFileValidatorTest.class.getClassLoader().getResource("emptyFile.txt").getPath());
        assertEquals(0, validator.validateTextFile(file)[0]);
    }

    @Test
    void givenFileWithOneEntryItShouldHaveOneRowAndOneColumn() throws FileNotFoundException {
        File file = new File(TextFileValidatorTest.class.getClassLoader().getResource("oneEntry.txt").getPath());
        assertArrayEquals(new int[]{1, 1}, validator.validateTextFile(file));
    }

    @Test
    void givenValidFilesItShouldReturnRightRowsAndColumns() throws FileNotFoundException {
        File directory = new File(TextFileValidatorTest.class.getClassLoader().getResource("validFiles").getPath());
        File[] validFiles = directory.listFiles();
        for (File file : validFiles) {
            assertArrayEquals(new int[]{7, 8}, validator.validateTextFile(file));
        }
    }

    @Test
    void givenFileWithInvalidEntryItShouldThrowEntryFormatException() throws FileNotFoundException {
        File file = new File(TextFileValidatorTest.class.getClassLoader().getResource("invalidEntry.txt").getPath());
        assertThrows(EntryFormatException.class, () -> validator.validateTextFile(file));
    }

    @Test
    void givenFileWithDifferentElementsPerRowItShouldThrowNonUniformEntriesLengthException() throws FileNotFoundException {
        File file = new File(TextFileValidatorTest.class.getClassLoader().getResource("nonUniformRows.txt").getPath());
        assertThrows(NonUniformEntriesLengthException.class, () -> validator.validateTextFile(file));
    }
}