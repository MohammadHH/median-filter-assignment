package org.exalt;

import org.exalt.configurations.Configuration;
import org.exalt.configurations.FileType;
import org.exalt.files.ReadFile;
import org.exalt.files.WriteFile;
import org.exalt.median.ChannelsMedianFilter;
import org.exalt.median.boundaries.ShrinkingWindowBoundaries;
import org.exalt.validators.ArgumentsValidator;
import org.exalt.validators.TextFileValidator;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        //validate passed arguments
        new ArgumentsValidator().validateArgs(args);
        // get app configuration based on the passed arguments
        Configuration configuration = new Configuration(args);

        File inputFile = new File(configuration.getInputFileName());
        short[][][] channels;
        if (configuration.getInputType().equals(FileType.TEXT_FILE)) {
            // validate text input file and get its dimensions
            int[] dimensions = new TextFileValidator().validateTextFile(inputFile);
            // read text inputFile and extract it as array of 2D channels
            channels = new ReadFile(configuration).readTextFile(inputFile, dimensions);
        } else {
            throw new UnsupportedOperationException("Image files aren't supported yet");
        }
        // apply median filter on each channel
        short[][][] filteredChannels = new ChannelsMedianFilter(configuration, new ShrinkingWindowBoundaries()).medianFilter(channels);
        // write filtered channels to a file
        File outputFile = new File(configuration.getOutputFileName());
        new WriteFile(configuration).writeFile(outputFile, filteredChannels);

    }
}
