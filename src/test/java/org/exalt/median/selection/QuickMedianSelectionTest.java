package org.exalt.median.selection;

import org.exalt.exceptions.EmptyArrayException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuickMedianSelectionTest {
    private static Map<String, MedianArray> arraysToTest;
    private static QuickMedianSelection selection;

    static class MedianArray {
        short median;
        short[] array;

        public MedianArray(int median, short[] array) {
            this.median = (short) median;
            this.array = array;
        }
    }

    @BeforeAll
    static void setUp() {
        arraysToTest = new HashMap<>();
        selection = new QuickMedianSelection();
        setUpArraysToTest();
    }

    private static void setUpArraysToTest() {
        arraysToTest.put("empty", new MedianArray(0, new short[]{}));
        arraysToTest.put("oneElement", new MedianArray(5, new short[]{5}));
        arraysToTest.put("sorted", new MedianArray(14, new short[]{5, 10, 12, 14, 17, 18, 20}));
        arraysToTest.put("repeatedElements", new MedianArray(8, new short[]{14, 6, 7, 9, 7, 8, 8, 12, 15, 14}));
        arraysToTest.put("evenElements", new MedianArray(96, new short[]{213, 23, 39, 176, 46, 145, 19, 67, 126, 207}));
    }


    @Test
    void givenEmptyArrayMedianShouldFail() {
        short[] empty = arraysToTest.get("empty").array;
        assertThrows(EmptyArrayException.class, () -> selection.median(empty));

    }

    @Test
    void givenOneElementArrayMedianIsThatElement() {
        MedianArray oneElement = arraysToTest.get("oneElement");
        assertEquals(oneElement.median, selection.median(oneElement.array));
    }

    @Test
    void givenSortedArrayMedianIsTheMiddle() {
        MedianArray sorted = arraysToTest.get("sorted");
        assertEquals(sorted.median, selection.median(sorted.array));
    }

    @Test
    void givenRepeatedElementsArrayMedianShouldBeCalculatedCorrectly() {
        MedianArray repeated = arraysToTest.get("repeatedElements");
        assertEquals(repeated.median, selection.median(repeated.array));
    }

    @Test
    void givenEvenArrayMedianIsAvgOfMiddles() {
        MedianArray even = arraysToTest.get("evenElements");
        Arrays.sort(even.array);
        assertEquals(even.median, selection.median(even.array));
    }

}