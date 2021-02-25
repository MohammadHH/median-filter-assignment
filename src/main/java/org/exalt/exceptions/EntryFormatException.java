package org.exalt.exceptions;

public class EntryFormatException extends RuntimeException {
    public EntryFormatException(String inputFileName, String entry, int line, int column) {
        super(String.format("Invalid input file (%s) malformed short value entry \"%s\", line %d, entry %d", inputFileName, entry, line, column));
    }
}
