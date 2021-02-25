package org.exalt.files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class ReadFileTest {
    private static ReadFile readFile;

    @BeforeAll
    static void setUp() {
        readFile = new ReadFile(Runtime.getRuntime().availableProcessors());
    }

    @Test
    void givenEmptyTextFileItShouldProduceEmptyChannels() throws IOException {
        File file = new File(ReadFile.class.getClassLoader().getResource("emptyFile.txt").getPath());
        Assertions.assertArrayEquals(new short[][][]{}, readFile.readTextFile(file, new int[]{0, -1}));
    }

    @Test
    void givenNonEmptyTextFileItShouldProduceRightChannels() throws IOException {
        File file = new File(ReadFile.class.getClassLoader().getResource("validFiles/valid.txt").getPath());
        Assertions.assertArrayEquals(new short[][][]{{
                {6, 59, 155, 81, 181, 81, 254, 12},
                {80, 57, 2, 162, 122, 212, 152, 214},
                {64, 235, 138, 114, 74, 88, 161, 167},
                {64, 57, 204, 37, 194, 99, 21, 244},
                {48, 40, 98, 94, 39, 122, 135, 88},
                {132, 145, 121, 50, 95, 243, 22, 235},
                {169, 152, 93, 45, 40, 122, 150, 168},
        }}, readFile.readTextFile(file, new int[]{7, 8}));

    }
}