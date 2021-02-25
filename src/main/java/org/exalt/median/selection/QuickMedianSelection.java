package org.exalt.median.selection;

import org.exalt.exceptions.EmptyArrayException;

// median selection based on quick sort
public class QuickMedianSelection implements Median {
    @Override
    public short median(short[] array) {
        if (array.length == 0) throw new EmptyArrayException("Can't get median from empty array");

        if (array.length == 1) return array[0];
        // get middle element
        short median = quickSelect(array, 0, array.length - 1, (array.length - 1) / 2);
        if (array.length % 2 == 0) {
            // for even elements, take the avg of the middle and the successive element
            median = avg(median, quickSelect(array, 0, array.length - 1, (array.length - 1) / 2 + 1));

        }
        return median;
    }

    private short avg(short a, short b) {
        return (short) ((a + b) / 2);
    }

    // select the kth smallest element from an array
    private short quickSelect(short[] array, int left, int right, int kth) {
        // elements that comes before pivot index are guaranteed to be less than the element at that index
        int pivotIndex = partition(array, left, right);
        // if pivotIndex changes to be k then element at kth is the smallest kth element
        if (kth == pivotIndex) return array[kth];
        //if pivotIndex is bigger than kth, kth element is at left subarray, else its at the right subarray
        if (pivotIndex > kth) return quickSelect(array, left, pivotIndex - 1, kth);
        else return quickSelect(array, pivotIndex + 1, right, kth);
    }

    // reorder the array such that all element that are less than the right one comes before it
    // change the right element index to come immediate after the elements that are less than it
    private int partition(short[] array, int left, int right) {
        int partitionIndex = left;
        int pivot = array[right];
        // go from left to right of the array
        for (int i = left; i < right; i++) {
            // put element that are less than pivot to its left
            if (array[i] < pivot) {
                swap(array, i, partitionIndex++);
            }
        }
        // put pivot element after immediate after the elements that are less than it
        swap(array, partitionIndex, right);
        return partitionIndex;
    }

    private void swap(short[] array, int index1, int index2) {
        short temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

}
