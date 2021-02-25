package org.exalt.exceptions;

public class EntryBoundException extends RuntimeException {
    public EntryBoundException(String inputFileName, String entry, int line, int column) {
        super(String.format("Invalid input file (%s) out of bound entry \"%s\", line %d, entry %d." +
                        "A valid entry can have values between 0 and 255",
                inputFileName, entry, line, column));
    }
}
