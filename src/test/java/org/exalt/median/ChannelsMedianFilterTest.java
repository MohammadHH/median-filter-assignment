package org.exalt.median;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ChannelsMedianFilterTest {
    private static ChannelsMedianFilter filter;
    private static short[][] channel;

    @BeforeAll
    static void setUp() {
        channel = new short[][]{
                {6, 59, 155, 81, 181, 81, 254, 12},
                {80, 57, 2, 162, 122, 212, 152, 214},
                {64, 235, 138, 114, 74, 88, 161, 167},
                {64, 57, 204, 37, 194, 99, 21, 244},
                {48, 40, 98, 94, 39, 122, 135, 88},
                {132, 145, 121, 50, 95, 243, 22, 235},
                {169, 152, 93, 45, 40, 122, 150, 168},
                {127, 216, 227, 80, 5, 175, 23, 10},
        };
        filter = new ChannelsMedianFilter(2, Runtime.getRuntime().availableProcessors());
    }

    @Test
    void testRightMedianEntriesWhenPerformingMedianFilterOverAChannel() throws InterruptedException {
        short[][] median = new short[][]{
                {64, 80, 81, 114, 138, 152, 156, 161},
                {64, 72, 80, 106, 130, 137, 156, 156},
                {64, 72, 80, 98, 122, 122, 128, 135},
                {80, 87, 94, 99, 114, 122, 128, 152},
                {121, 96, 94, 98, 98, 99, 122, 135},
                {127, 96, 94, 98, 95, 94, 110, 122},
                {129, 109, 94, 96, 94, 91, 108, 128},
                {145, 129, 121, 121, 93, 80, 108, 150},
        };
        assertArrayEquals(median, filter.getChannelMedian(channel));
    }
}