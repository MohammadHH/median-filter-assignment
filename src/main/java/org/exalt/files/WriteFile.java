package org.exalt.files;


import org.exalt.configurations.Configuration;
import org.exalt.configurations.FileType;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {
    private Configuration configuration;

    public WriteFile(Configuration configuration) {
        this.configuration = configuration;
    }

    public void writeFile(File file, short[][][] channels) {
        if (configuration.getInputType().equals(FileType.TEXT_FILE)) {
            writeTextFile(file, channels[0]);
        } else {
            // write image file
        }
    }

    public void writeTextFile(File file, short[][] channel) {
        // number of rows in the channel
        int rows = channel.length;
        // number of columns in the channel
        int columns = channel[0].length;
        try (PrintWriter printWriter = new PrintWriter(file)) {
            // go through channel rows and columns and write each entry into the file
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    printWriter.printf(j == columns - 1 ? "%3d" : "%3d ", channel[i][j]);
                }
                printWriter.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
