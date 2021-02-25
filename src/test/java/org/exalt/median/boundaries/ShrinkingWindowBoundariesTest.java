package org.exalt.median.boundaries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ShrinkingWindowBoundariesTest {
    private static ShrinkingWindowBoundaries shrinkingWindow;
    private static short[][] channel;
    private static short[] windowArray;

    @BeforeAll
    static void setUp() {
        shrinkingWindow = new ShrinkingWindowBoundaries();
        channel = new short[][]{
                {6, 59, 155, 81, 181, 81, 254, 12}
                , {80, 57, 2, 162, 122, 212, 152, 214}
                , {64, 235, 138, 114, 74, 88, 161, 167}
                , {64, 57, 204, 37, 194, 99, 21, 244}
                , {48, 40, 98, 94, 39, 122, 135, 88}
                , {132, 145, 121, 50, 95, 243, 22, 235}
                , {169, 152, 93, 45, 40, 122, 150, 168}
        };
        //windowArray at entry (3,4)[194]
        windowArray = new short[]{
                2, 162, 122, 212, 152,
                138, 114, 74, 88, 161,
                204, 37, 194, 99, 21,
                98, 94, 39, 122, 135,
                121, 50, 95, 243, 22,
        };
    }

    @Test
    void givenEdgePositionsWindowBoundariesShouldShrinkAsExpected() {
        Assertions.assertAll(() -> {
            // test top left edge
            assertArrayEquals(new int[][]{{0, 0}, {2, 2}},
                    shrinkingWindow.getBoundaries(7, 8, 2, new int[]{0, 0}));
        }, () -> {
            // test top right edge
            assertArrayEquals(new int[][]{{0, 5}, {2, 7}},
                    shrinkingWindow.getBoundaries(7, 8, 2, new int[]{0, 7}));
        }, () -> {
            // test bottom left edge
            assertArrayEquals(new int[][]{{4, 0}, {6, 2}},
                    shrinkingWindow.getBoundaries(7, 8, 2, new int[]{6, 0}));
        }, () -> {
            // test bottom right edge
            assertArrayEquals(new int[][]{{4, 5}, {6, 7}},
                    shrinkingWindow.getBoundaries(7, 8, 2, new int[]{6, 7}));
        });
    }

    @Test
    void givenLargeWindowSizeWindowShouldShrinkToMaximumAvailableSize() {
        assertArrayEquals(new int[][]{{0, 0}, {6, 7}},
                shrinkingWindow.getBoundaries(7, 8, 15, new int[]{3, 4}));
    }

    @Test
    void testRightBoundariesGivenSomePosition() {
        assertArrayEquals(new int[][]{{1, 2}, {5, 6}},
                shrinkingWindow.getBoundaries(7, 8, 2, new int[]{3, 4}));
    }

    @Test
    void testWellConstructedArrayGivenAMatrixAndBoundaries() {
        assertArrayEquals(
                windowArray,
                shrinkingWindow.getArrayFromMatrix(new int[]{1, 2}, new int[]{5, 6}, channel)
        );
    }
}