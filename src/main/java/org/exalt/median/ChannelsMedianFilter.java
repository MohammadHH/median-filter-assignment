package org.exalt.median;


import org.exalt.median.boundaries.ShrinkingWindowBoundaries;
import org.exalt.median.boundaries.WindowBoundaries;
import org.exalt.median.selection.Median;
import org.exalt.median.selection.QuickMedianSelection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Get median from given 2D channels
public class ChannelsMedianFilter {
    private Median medianStrategy;
    private WindowBoundaries utility;
    private int windowSize;
    private int numberOfThreads;

    public ChannelsMedianFilter(int windowSize, int numberOfThreads) {
        // use quick selection strategy by default
        this.medianStrategy = new QuickMedianSelection();
        // use shrinking boundaries strategy by default
        this.utility = new ShrinkingWindowBoundaries();
        this.windowSize = windowSize;
        this.numberOfThreads = numberOfThreads;
    }

    public Median getMedianStrategy() {
        return medianStrategy;
    }

    public void setMedianStrategy(Median medianStrategy) {
        this.medianStrategy = medianStrategy;
    }

    public WindowBoundaries getWindowBoundaryStrategy() {
        return utility;
    }

    public void setWindowBoundaryStrategy(WindowBoundaries utility) {
        this.utility = utility;
    }

    // return median filter for given channels
    public short[][][] medianFilter(short[][][] channels) {
        short[][][] filteredChannels = new short[channels.length][][];
        // loop through channels
        for (int i = 0; i < channels.length; i++) {
            filteredChannels[i] = getChannelMedian(channels[i]);
        }
        return filteredChannels;
    }

    // return median filter for given 2D channel
    public short[][] getChannelMedian(short[][] channel) {
        int rows = channel.length;
        int columns = channel[0].length;
        short[][] medianChannel = new short[rows][columns];
        // initialize fixed threads pool
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        // loop over each row in the channel
        for (int i = 0; i < rows; i++) {
            int k = i;//k is effectively final and can be used in lambda
            // submit finding median for entries in each row in the channel as a task
            // for concurrent execution
            executor.submit(() -> {
                // for each entry in a row
                for (int j = 0; j < columns; j++) {
                    int[][] boundaries;
                    int[] start;
                    int[] end;
                    // get start and end point for the sliding window
                    boundaries = utility.getBoundaries(rows, columns, windowSize, new int[]{k, j});
                    start = boundaries[0];
                    end = boundaries[1];
                    // convert sliding window into array and find its median
                    // store the result in the median channel
                    medianChannel[k][j] = medianStrategy.median(utility.getArrayFromMatrix(start, end, channel));
                }
            });
        }
        // no further tasks
        executor.shutdown();
        try {
            // wait for tasks to complete
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return medianChannel;
    }
}
