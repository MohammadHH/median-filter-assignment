package org.exalt.configurations;

import org.exalt.exceptions.ArgumentFormatException;
import org.exalt.exceptions.ArgumentsLengthException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class validates and extract passed command line arguments
 * the class saves the following
 * inputFileName the first passed argument
 * outputFileName the second passed argument
 * windowSize the third passed argument
 * inputType filled based on the passed input
 * numberOfThreads by default it is configured
 * to be the available processors on the machine the code is running on
 * this can be changed manually with {@link #setNumberOfThreads(int threadsNumber)}
 */
public class Configuration {
    private String inputFileName;
    private String outputFileName;
    private int windowSize;
    private FileType inputType;
    private int numberOfThreads;

    public Configuration(String[] args) {
        // validate the passed arguments
        validateArgs(args);
        // extract input,output file names and window size
        inputFileName = args[0];
        outputFileName = args[1];
        windowSize = Integer.valueOf(args[2]);
        // initiate thread numbers as the number of processors available for this machine
        numberOfThreads = Runtime.getRuntime().availableProcessors();
        try {
            // try extracting content type of file
            String contentType = extractInputContentType();
            // set input type based on the content type
            setInputType(contentType);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Validate the given command line arguments,
     * arguments length should be 3
     * third argument is window size and should be a parsable integer
     * should any condition fail, the program stops and throws a dedicated exception
     *
     * @param args command line arguments
     */
    private void validateArgs(String[] args) {
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

    private String extractInputContentType() throws IOException {
        File inputFile = new File(inputFileName);
        if (!inputFile.exists()) {
            throw new FileNotFoundException(String.format(" %s (No such file)", inputFileName));
        }
        return Files.probeContentType(inputFile.toPath());
    }

    private void setInputType(String contentType) {
        if (contentType != null && contentType.contains("image")) {
            inputType = FileType.IMAGE_FILE;
        } else {
            // if content type is not an image, consider it a text
            inputType = FileType.TEXT_FILE;
        }
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public FileType getInputType() {
        return inputType;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

}
