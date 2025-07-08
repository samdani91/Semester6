package io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {
    private FileIO fileio;
    private final PrintStream originalErr = System.err;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    void setUp() {
        fileio = new FileIO();
        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() {
        System.setErr(originalErr);
    }

    @Test
    void read_empty_file() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileio.readFile("src/test/resources/empty_file.txt");
        });
    }
    @Test
    void test_io_exception() {
        File tempFile = new File("src/test/resources/restricted_file.txt");
        try {
            tempFile.createNewFile();
            tempFile.setReadable(false);
            assertThrows(IllegalArgumentException.class, () -> {
                fileio.readFile(tempFile.getPath());
            });
            String errOutput = errContent.toString();
            assertTrue(errOutput.contains("java.io.FileNotFoundException") ||
                            errOutput.contains("Permission denied"),
                    "Expected FileNotFoundException stack trace in System.err output");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to create test file: " + e.getMessage());
        } finally {
            tempFile.setReadable(true);
            tempFile.delete();
        }
    }

    @Test
    void non_existing_file() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileio.readFile("/home/noFile.txt");
        });
    }

    @Test
    void check_invalid_entries(){
        assertThrows(NumberFormatException.class, () -> {
            fileio.readFile("src/test/resources/grades_invalid.txt");
        });
    }

    @Test
    void check_valid_entries(){
        int[] expected = {3, 9, 0, 2, 10, 9, 3, 8, 0, 3};
        int[] actual = fileio.readFile("src/test/resources/grades_valid.txt");

        assertArrayEquals(expected, actual);

    }
}