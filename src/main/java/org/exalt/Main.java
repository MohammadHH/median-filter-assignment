package org.exalt;

import org.exalt.configurations.Configuration;

public class Main {
    public static void main(String[] args) {
        // get app configuration based on the passed arguments
        Configuration configuration = new Configuration(args);
    }
}
