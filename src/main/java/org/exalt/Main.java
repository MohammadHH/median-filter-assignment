package org.exalt;

import org.exalt.configurations.Configuration;
import org.exalt.configurations.FileType;
import org.exalt.files.ReadFile;
import org.exalt.files.WriteFile;
import org.exalt.median.ChannelsMedianFilter;
import org.exalt.validators.ArgumentsValidator;
import org.exalt.validators.TextFileValidator;

import java.io.File;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            System.setProperty("java.util.logging.SimpleFormatter.format",
                    "%4$s: %5$s %n");
            long startTime = System.nanoTime();

            logger.info("Validate arguments ...");
            //validate passed arguments
            new ArgumentsValidator().validateGivenArguments(args);

            logger.info("Extract app configuration ...");
            // get app configuration based on the passed arguments
            Configuration configuration = new Configuration(args);
            int windowSize = configuration.getWindowSize();
            int numberOfThreads = configuration.getNumberOfThreads();
            logger.info("Window size: " + windowSize);
            logger.info("Number of threads: " + numberOfThreads);

            // Read input file
            File inputFile = new File(configuration.getInputFileName());
            short[][][] channels;
            if (configuration.getInputType().equals(FileType.TEXT_FILE)) {
                logger.info("Validate input text file ...");
                // validate text input file and get its dimensions [numberOfRows,numberOfElementsPerEachRow]
                int[] dimensions = new TextFileValidator().validateTextFile(inputFile);
                logger.info(String.format("Rows: %d, Columns: %d", dimensions[0], dimensions[1]));
                logger.info("Read input text file ...");
                // read text inputFile and extract it as array of 2D channels
                channels = new ReadFile(numberOfThreads).readTextFile(inputFile, dimensions);
            } else {
                throw new UnsupportedOperationException("Image files aren't supported yet");
            }

            logger.info("Apply median filter ...");
            // apply median filter on each channel
            short[][][] filteredChannels = new ChannelsMedianFilter(windowSize, numberOfThreads).applyMedianFilterOnChannels(channels);

            logger.info("Write resulted file ...");
            // write filtered channels to a file
            File outputFile = new File(configuration.getOutputFileName());
            new WriteFile(configuration).writeFile(outputFile, filteredChannels);

            logger.info(String.format("Took %.3f seconds to execute", (System.nanoTime() - startTime) * 1.0 / 1_000_000_000L));
        } catch (Exception exception) {
            // Handle any caught exception and log it to the user
            logger.severe(exception.getMessage());
        }
    }
}
