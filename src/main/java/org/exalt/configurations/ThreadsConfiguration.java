package org.exalt.configurations;

public class ThreadsConfiguration {
    private int numberOfThreads;

    public ThreadsConfiguration(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }
}
