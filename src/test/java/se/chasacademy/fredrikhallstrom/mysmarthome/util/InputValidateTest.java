package se.chasacademy.fredrikhallstrom.mysmarthome.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidateTest {
    private final InputValidate inputValidate = new InputValidate() {};

    @Test
    public void shouldReturnIntWhenStringIsFourDigitIdTest() {
        // Arrange
        String input = "1000";

        // Act
        int result = inputValidate.validateInputStringToInt(input);

        // Assert
        assertEquals(1000, result);
    }

    @Test
    public void shouldReturnZeroWhenStringIsZeroTest() {
        // Arrange
        String input = "0";

        // Act
        int result = inputValidate.validateInputStringToInt(input);

        // Assert
        assertEquals(0, result);
    }

    @Test
    public void shouldReturnMaxIntWhenStringIsMaxIntTest() {
        // Arrange
        String input = String.valueOf(Integer.MAX_VALUE);

        // Act
        int result = inputValidate.validateInputStringToInt(input);

        // Assert
        assertEquals(Integer.MAX_VALUE, result);
    }

    @Test
    public void shouldThrowMustBeANumberExceptionWhenStringIsLettersTest() {
        // Arrange
        String input = "abc";

        // Act
        Executable throwAct = () -> inputValidate.validateInputStringToInt(input);

        // Assert
        assertThrows(MustBeANumberException.class, throwAct);
    }

    @Test
    public void shouldThrowMustBeANumberExceptionWhenStringIsNullTest() {
        // Arrange
        String input = null;

        // Act
        Executable throwAct = () -> inputValidate.validateInputStringToInt(input);

        // Assert
        assertThrows(MustBeANumberException.class, throwAct);
    }

    @Test
    public void shouldThrowMustBeANumberExceptionWhenNumberIsLargerThanMaxIntTest() {
        // Arrange
        String input = "2147483648";

        // Act
        Executable throwAct = () -> inputValidate.validateInputStringToInt(input);

        // Assert
        assertThrows(MustBeANumberException.class, throwAct);
    }
}
