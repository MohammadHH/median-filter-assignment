package org.exalt.median.boundaries;

// handle entries at edges by shrinking window size
public class ShrinkingWindowBoundaries implements WindowBoundaries {

    public int[][] getBoundaries(int columns, int rows, int windowSize, int[] entryPosition) {
        // rows above entry that are available for the window
        int above = Math.min(entryPosition[0], windowSize);
        // rows below entry that are available for the window
        int below = Math.min(rows - entryPosition[0] - 1, windowSize);
        // columns to left of entry that are available for the window
        int left = Math.min(entryPosition[1], windowSize);
        // columns to right of entry that are available for the window
        int right = Math.min(columns - entryPosition[1] - 1, windowSize);
        // return window starting and ending point
        return new int[][]{
                {entryPosition[0] - above, entryPosition[1] - left},
                {entryPosition[0] + below, entryPosition[1] + right}
        };
    }

    public short[] getArrayFromMatrix(int[] start, int[] end, short[][] channel) {
        // number of rows in the window
        int rows = end[0] - start[0] + 1;
        // number of columns in the window
        int columns = end[1] - start[1] + 1;
        short[] array = new short[columns * rows];
        int k = 0;
        // loop over the window and fill the array
        for (int i = start[0]; i < start[0] + rows; i++) {
            for (int j = start[1]; j < start[1] + columns; j++) {
                array[k++] = channel[i][j];
            }
        }
        return array;
    }
}
