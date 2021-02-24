package org.exalt.validators;

import org.exalt.exceptions.ArgumentFormatException;
import org.exalt.exceptions.ArgumentsLengthException;

public class ArgumentsValidator {
    /**
     * Validate the given command line arguments,
     * arguments length should be 3
     * third argument is window size and should be a parsable integer
     * should any condition fail, the program stops and throws a dedicated exception
     *
     * @param args command line arguments
     */
    public void validateArgs(String[] args) {
        if (args.length != 3) {
            throw new ArgumentsLengthException(String.format("Invalid number of arguments, expected %d arguments but provided with %d", 3, args.length));
        }
        try {
            // try getting window size
            Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            throw new ArgumentFormatException(String.format("Malformed window size \"%s\"", args[2]));
        }
    }
}
