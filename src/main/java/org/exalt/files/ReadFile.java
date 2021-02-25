package org.exalt.files;


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
    private int numberOfThreads;

    public ReadFile(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    // returns a text file as an array with single 2D array (text file has one channel)
    public short[][][] readTextFile(File file, int[] dimensions) {
        // extract file dimensions
        int lines = dimensions[0];
        int elementsPerLine = dimensions[1];
        if (lines == 0) {
            // empty file
            return new short[][][]{};
        }
        short[][] channel = new short[lines][elementsPerLine];

        // parse the file and fill the channel
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
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
                // submit line parsing to task queue for concurrent parsing
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
