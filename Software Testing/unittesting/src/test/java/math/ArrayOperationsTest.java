package math;

import io.FileIO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class ArrayOperationsTest {
    private ArrayOperations arrayOperations;
    private FileIO fileio;
    private MyMath myMath;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        arrayOperations = new ArrayOperations();
        fileio = new FileIO();
        myMath = new MyMath();
        tempFile = Files.createTempFile("testNumbers", ".txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        if (tempFile != null) {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void findPrimesInFile() {
        String filePath = "/home/samdani1412/Desktop/Semester6/Software Testing/unittesting/src/test/resources/grades_valid.txt";
        int[] expected = {3, 2, 3, 3};
        int[] result = arrayOperations.findPrimesInFile(fileio, filePath, myMath);
        assertArrayEquals(expected, result);
    }

    @Test
    void findPrimesInFile_withMixedNumbers_returnsPrimeNumbers() throws IOException {
        String content = "4\n3\n6\n2\n7\n8\n5";
        Files.writeString(tempFile, content);
        String filePath = tempFile.toString();
        int[] expected = {3, 2, 7, 5};
        int[] result = arrayOperations.findPrimesInFile(fileio, filePath, myMath);
        assertArrayEquals(expected, result);
    }

    @Test
    void findPrimesInFile_withNoPrimes_returnsEmptyArray() throws IOException {
        String content = "4\n6\n8\n9";
        Files.writeString(tempFile, content);
        String filePath = tempFile.toString();
        int[] expected = {};
        int[] result = arrayOperations.findPrimesInFile(fileio, filePath, myMath);
        assertArrayEquals(expected, result);
    }
}