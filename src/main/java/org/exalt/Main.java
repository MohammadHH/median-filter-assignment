package org.exalt;

import org.exalt.configurations.Configuration;
import org.exalt.files.ReadFile;
import org.exalt.files.WriteFile;
import org.exalt.median.ChannelsMedianFilter;
import org.exalt.median.boundaries.ShrinkingWindowBoundaries;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // get app configuration based on the passed arguments
        Configuration configuration = new Configuration(args);
        // read inputFile and extract it as array of 2D channels
        short[][][] channels = new ReadFile(configuration).readFile();
        // channels after applying median filter on each of them
        short[][][] filteredChannels = new ChannelsMedianFilter(configuration, new ShrinkingWindowBoundaries()).medianFilter(channels);
        // write filtered channels to a file
        File outputFile = new File(configuration.getOutputFileName());
        new WriteFile(configuration).writeFile(outputFile, filteredChannels);

    }
}
