package io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {
    private FileIO fileio;

    @BeforeEach
    void setUp() {
        fileio = new FileIO();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void read_empty_file() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileio.readFile("/home/samdani1412/Desktop/Semester6/Software Testing/unittesting/src/test/resources/empty_file.txt");
        });
    }

    @Test
    void non_existing_file() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileio.readFile("/home/samdani1412/noFile.txt");
        });
    }

    @Test
    void check_invalid_entries(){
        assertThrows(NumberFormatException.class, () -> {
            fileio.readFile("/home/samdani1412/Desktop/Semester6/Software Testing/unittesting/src/test/resources/grades_invalid.txt");
        });
    }

    @Test
    void check_valid_entries(){
        int[] expected = {3, 9, 0, 2, 10, 9, 3, 8, 0, 3};
        int[] actual = fileio.readFile("/home/samdani1412/Desktop/Semester6/Software Testing/unittesting/src/test/resources/grades_valid.txt");

        assertArrayEquals(expected, actual);

    }
}