package math;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArithmeticOperationsTest {

    private ArithmeticOperations arithmeticOperations;

    @BeforeEach
    void setUp() {
        arithmeticOperations = new ArithmeticOperations();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void divide_positive_numbers() {
        double expected = 5.33;
        double actual = arithmeticOperations.divide(16,3);

        assertEquals(expected, actual,1e-2);
    }

    @Test
    void divide_negative_numbers() {
        double expected = 5.33;
        double actual = arithmeticOperations.divide(-16,-3);

        assertEquals(expected, actual,1e-2);
    }

    @Test
    void divide_denominator_zero() {
        assertThrows(ArithmeticException.class, () -> {
            arithmeticOperations.divide(5, 0);
        });
    }

    @Test
    void divide_numerator_zero() {
        double expected = 0;
        double actual = arithmeticOperations.divide(0,3);

        assertEquals(expected, actual,1e-2);
    }

    @Test
    void divide_by_negativeDenominator() {
        double expected = -5.33;
        double actual = arithmeticOperations.divide(16,-3);

        assertEquals(expected, actual,1e-2);
    }

    @Test
    void divide_with_negativeNumerator() {
        double expected = -5.33;
        double actual = arithmeticOperations.divide(-16,3);

        assertEquals(expected, actual,1e-2);
    }

    @Test
    void multiply_positive_arguments() {
        int expected = 15;
        int actual = arithmeticOperations.multiply(5,3);

        assertEquals(expected, actual);
    }

    @Test
    void multiply_both_negative_arguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            arithmeticOperations.multiply(-5, -3);
        });
    }

    @Test
    void multiply_x_negative_arguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            arithmeticOperations.multiply(-5,3);
        });
    }

    @Test
    void multiply_y_negative_arguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            arithmeticOperations.multiply(5,-3);
        });
    }

    @Test
    void multiply_x_value_zero() {
        int expected = 0;
        int actual = arithmeticOperations.multiply(0,3);

        assertEquals(expected, actual);
    }

    @Test
    void multiply_y_value_zero() {
        int expected = 0;
        int actual = arithmeticOperations.multiply(3,0);

        assertEquals(expected, actual);
    }

    @Test
    void multiply_integer_fit_test(){
        assertThrows(IllegalArgumentException.class, () -> {
            arithmeticOperations.multiply(2147483647,2147483647);
        });

    }

    @Test
    void multiply_y_value_zero_with_large_x() {
        int expected = 0;
        int actual = arithmeticOperations.multiply(Integer.MAX_VALUE, 0);
        assertEquals(expected, actual);
    }

    @Test
    void test_multiply__max_boundary() {
        int maxDiv2 = Integer.MAX_VALUE / 2;
        assertEquals(maxDiv2 * 2, arithmeticOperations.multiply(maxDiv2, 2));
    }
}