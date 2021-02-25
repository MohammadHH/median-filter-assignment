package org.exalt.exceptions;

public class NonUniformEntriesLengthException extends RuntimeException {
    public NonUniformEntriesLengthException(String inputFileName, int line, int expectedNumberOfElements, int numberOfElements) {
        super(String.format("Invalid input file (%s) line %d, expected to find %d element(s) in this line but found %d element(s)", inputFileName, line, expectedNumberOfElements, numberOfElements));
    }
}
