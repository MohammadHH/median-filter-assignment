package org.exalt.files;


import org.exalt.configurations.Configuration;
import org.exalt.configurations.FileType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This read class reads an input file and extract its channels
 */
public class ReadFile {
    private Configuration configuration;

    public ReadFile(Configuration configuration) {
        this.configuration = configuration;
    }

    public short[][][] readFile() {
        // call appropriate read method based on the input fileType
        if (configuration.getInputType().equals(FileType.TEXT_FILE)) {
            return readTextFile(new File(configuration.getInputFileName()), new TextFileValidator());
        } else {
            // Image Type
            return new short[][][]{};
        }
    }

    // returns a text file as an array with single 2D array (text file has one channel)
    public short[][][] readTextFile(File file, TextFileValidator validator) {
        // validate the given text file & get channel dimension
        int[] textFileInfo = validator.validateTextFile(file);

        // extract file dimensions
        int lines = textFileInfo[0];
        int elementsPerLine = textFileInfo[1];

        short[][] channel = new short[lines][elementsPerLine];

        // parse the file and fill the channel
        ExecutorService executor = Executors.newFixedThreadPool(configuration.getNumberOfThreads());
        try (Scanner scanner = new Scanner(file);) {
            // for each line in the file
            for (int i = 0; i < lines; i++) {
                // get the line
                String line = scanner.nextLine().trim();
                // discard empty lines
                if (Objects.equals(line, "")) continue;
                //extract line entries
                String[] entries = line.split(scanner.delimiter().pattern());
                int k = i;// k is effectively final and can be used in lambda
                // submit line parsing to task queue
                executor.submit(() -> {
                    for (int j = 0; j < elementsPerLine; j++) {
                        channel[k][j] = Short.valueOf(entries[j]);
                    }
                });
            }
            // no further tasks
            executor.shutdown();
            // wait for tasks to complete
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
            Thread.currentThread().interrupt();
        }
        // return the parsed text file as channel
        short[][][] readFile = new short[1][][];
        readFile[0] = channel;
        return readFile;
    }

}
