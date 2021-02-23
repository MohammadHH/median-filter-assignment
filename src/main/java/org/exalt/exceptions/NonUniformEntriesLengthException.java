package org.exalt.exceptions;

public class NonUniformEntriesLengthException extends RuntimeException {
    public NonUniformEntriesLengthException(int line, int expectedNumberOfElements, int numberOfElements) {
        super(String.format("Line %d, Expected to find %d element(s) in this line but found %d element(s)", line, expectedNumberOfElements, numberOfElements));
    }
}
