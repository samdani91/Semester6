package math;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMathTest {
    private MyMath myMath;

    @BeforeEach
    void setUp() {
        myMath = new MyMath();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void factorial_with_range() {
        int expected = 120;
        int actual = myMath.factorial(5);

        assertEquals(expected, actual);
    }

    @Test
    void factorial_outside_lowrange() {
        assertThrows(IllegalArgumentException.class, () -> {
            myMath.factorial(-1);
        });
    }

    @Test
    void factorial_outside_highrange() {
        assertThrows(IllegalArgumentException.class, () -> {
            myMath.factorial(13);
        });
    }

    @Test
    void isPrime() {
        boolean expected = true;
        boolean actual = myMath.isPrime(2);
        assertEquals(expected, actual);
    }

    @Test
    void isPrim_less_than_two() {
        assertThrows(IllegalArgumentException.class, () -> {
            myMath.isPrime(1);
        });
    }
}