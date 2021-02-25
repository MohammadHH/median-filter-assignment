package org.exalt.median.selection;

// select median of given 1D array
public interface Median {
    /**
     * returns the median of a given array
     * the array maybe reordered based on the specific implementation
     *
     * @param array array to apply median on
     * @return the median element
     **/
    short getMedianElementFromGivenArray(short[] array);
}
