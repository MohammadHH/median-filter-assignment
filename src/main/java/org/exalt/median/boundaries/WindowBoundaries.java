package org.exalt.median.boundaries;

// Find window starting and end point
public interface WindowBoundaries {
    /**
     * return window starting and ending point
     * (i.e what neighboring entries will affect median calculation for the given entry position)
     *
     * @param columns       number of columns in channel
     * @param rows          number of rows in channel
     * @param windowSize    the size of window
     * @param entryPosition {row,column} at which entry is located
     * @return int[]{int[]startPoint{row,column},int[]endPoint{row,column}}
     */
    int[][] getBoundaries(int columns, int rows, int windowSize, int[] entryPosition);

    /**
     * boundaries helper method
     * returns array representing window elements
     * implemented based on how boundaries are calculated
     *
     * @param start   start point int[]{row,column}
     * @param end     end point  int[]{row,column}
     * @param channel the channel to get the matrix from
     * @return newly created array from the given parameters
     */
    short[] getArrayFromMatrix(int[] start, int[] end, short[][] channel);
}
