package org.exalt;

import org.exalt.configurations.Configuration;
import org.exalt.files.ReadFile;

public class Main {
    public static void main(String[] args) {
        // get app configuration based on the passed arguments
        Configuration configuration = new Configuration(args);
        // read inputFile and extract it as array of 2D channels
        short[][][] channels = new ReadFile(configuration).readFile();

    }
}
