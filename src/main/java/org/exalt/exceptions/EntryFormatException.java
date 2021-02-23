package org.exalt.exceptions;

public class EntryFormatException extends RuntimeException {
    public EntryFormatException(String entry, int line, int column) {
        super(String.format("Malformed short value entry \"%s\", line %d, entry %d", entry, line, column));
    }
}
