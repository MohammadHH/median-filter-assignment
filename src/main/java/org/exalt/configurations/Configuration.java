package org.exalt.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class extract passed command line arguments
 * the class saves the following
 * inputFileName the first passed argument
 * outputFileName the second passed argument
 * windowSize the third passed argument
 * inputType filled based on the passed input
 * numberOfThreads by default is set to the available processors
 * on the machine that will run the app
 * this can be changed manually with {@link #setNumberOfThreads(int threadsNumber)}
 */
public class Configuration {
    private String inputFileName;
    private String outputFileName;
    private WindowConfiguration windowConfiguration;
    private FileType inputType;
    private ThreadsConfiguration threadsConfiguration;

    public Configuration(String[] args) throws IOException {
        // extract input,output file names and window size
        inputFileName = args[0];
        outputFileName = args[1];
        windowConfiguration = new WindowConfiguration(Integer.parseInt(args[2]));
        // initiate thread numbers as the number of processors available for this machine
        threadsConfiguration = new ThreadsConfiguration(Runtime.getRuntime().availableProcessors());
        // try extracting content type of file
        String contentType = extractInputContentType();
        // set input type based on the content type
        setInputType(contentType);
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
        } else if (contentType == null || contentType.contains("text")) {
            inputType = FileType.TEXT_FILE;
        } else {
            throw new UnsupportedOperationException(contentType + " types are not supported");
        }
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public int getWindowSize() {
        return windowConfiguration.getWindowSize();
    }

    public FileType getInputType() {
        return inputType;
    }

    public int getNumberOfThreads() {
        return threadsConfiguration.getNumberOfThreads();
    }

    // in case one want to modify number of threads to number other than default
    public void setNumberOfThreads(int numberOfThreads) {
        this.threadsConfiguration.setNumberOfThreads(numberOfThreads);

    }

}
