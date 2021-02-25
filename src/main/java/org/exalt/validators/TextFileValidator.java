package org.exalt.validators;

import org.exalt.exceptions.EntryBoundException;
import org.exalt.exceptions.EntryFormatException;
import org.exalt.exceptions.NonUniformEntriesLengthException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class TextFileValidator {

    /**
     * Ensure text file is formed correctly
     * a valid text file should comply with following
     * it should have its entries separated by spaces and/or tabs
     * different rows are separated by end of line
     * number of elements should be the same in each row
     * a file could have empty lines which will be discarded
     *
     * @param file text file to validate
     * @return int[]{numberOfRows,numberOfColumns}
     */
    public int[] validateTextFile(File file) throws FileNotFoundException {
        // row,column,token is handy to show parsing error location
        int row = 0;
        int column = 0;
        String entry = null;

        // number of elements per each line is set after parsing the first non empty line
        int numberOfElementsPerLine = -1;
        try (Scanner scanner = new Scanner(file)) {
            // parse the file line by line
            while (scanner.hasNextLine()) {
                // get the line, trimming is necessary to check for empty lines
                // and to avoid empty entries in non empty lines
                String line = scanner.nextLine().trim();
                // discard empty lines
                if (Objects.equals(line, "")) continue;
                // get line entries
                String[] entries = line.split(scanner.delimiter().pattern());
                // parse line entries
                for (int i = 0; i < entries.length; i++) {
                    entry = entries[i];
                    short parsedEntry = Short.parseShort(entry);
                    if (parsedEntry < 0 || parsedEntry > 255) {
                        throw new EntryBoundException(file.getName(), entry, row, column);

                    }
                    column++;
                }
                // is this the first non empty line
                if (numberOfElementsPerLine == -1) {
                    numberOfElementsPerLine = column;
                } else if (numberOfElementsPerLine != column) {
                    // entries length on a subsequent non empty line is not the same as the rest
                    throw new NonUniformEntriesLengthException(file.getName(), row, numberOfElementsPerLine, column);
                }
                // return column at 0 for the next line and advance row
                column = 0;
                row++;
            }
        } catch (NumberFormatException ex) {
            // an entry has been wrongly parsed
            throw new EntryFormatException(file.getName(), entry, row, column);
        }
        // return the text file 2D channel size
        return new int[]{row, numberOfElementsPerLine};
    }
}
