package org.exalt.validators;

import org.exalt.exceptions.ArgumentFormatException;
import org.exalt.exceptions.ArgumentsLengthException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArgumentsValidatorTest {
    private static ArgumentsValidator validator;
    private static Map<String, String[]> arguments;

    @BeforeAll
    static void setUp() {
        validator = new ArgumentsValidator();
        arguments = new HashMap<>();
        arguments.put("empty", new String[]{});
        arguments.put("lessThanThree", new String[]{"f1", "f2"});
        arguments.put("moreThanThree", new String[]{"f1", "f2", "50", "f3"});
        arguments.put("nonParsableInteger", new String[]{"f1", "f2", "50x"});
        arguments.put("valid", new String[]{"f1", "f2", "50"});
    }

    @Test
    void givenEmptyStringThrowArgumentsLengthException() {
        String[] empty = arguments.get("empty");
        assertThrows(ArgumentsLengthException.class, () -> validator.validateArgs(empty));
    }

    @Test
    void givenTwoArgsThrowArgumentsLengthException() {
        String[] twoArgs = arguments.get("lessThanThree");
        assertThrows(ArgumentsLengthException.class, () -> validator.validateArgs(twoArgs));

    }

    @Test
    void givenFourArgsThrowArgumentsLengthException() {
        String[] fourArgs = arguments.get("moreThanThree");
        assertThrows(ArgumentsLengthException.class, () -> validator.validateArgs(fourArgs));
    }

    @Test
    void givenNonParsableIntegerThrowArgumentsFormatException() {
        String[] nonParsable = arguments.get("nonParsableInteger");
        assertThrows(ArgumentFormatException.class, () -> validator.validateArgs(nonParsable));
    }

    @Test
    void givenValidArgsNoExceptionIsThrown() {
        String[] valid = arguments.get("valid");
        assertDoesNotThrow(() -> validator.validateArgs(valid));
    }

}