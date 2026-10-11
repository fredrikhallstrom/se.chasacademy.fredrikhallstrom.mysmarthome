package se.chasacademy.fredrikhallstrom.mysmarthome.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchSortTypeIdNotValidException;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SearchSortingType;

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

    @Test
    public void shouldReturnStringFromInputValidateAsString() {
        // Arrange
        String input = "abc";

        // Act
        String validatedInput = inputValidate.validateInputStringNoSpecialChars(input);

        // Assert
        assertEquals(input, validatedInput);

    }

    @Test
    public void shouldReturnAscendingWhenSortTypeIdIsOneTest() {
        // Arrange
        int sortTypeId = 1;

        // Act
        SearchSortingType sortingType = inputValidate.validateInputSortType(sortTypeId);

        // Assert
        assertEquals(SearchSortingType.ASCENDING, sortingType);
    }

    @Test
    public void shouldReturnDescendingWhenSortTypeIdIsTwoTest() {
        // Arrange
        int sortTypeId = 2;

        // Act
        SearchSortingType sortingType = inputValidate.validateInputSortType(sortTypeId);

        // Assert
        assertEquals(SearchSortingType.DESCENDING, sortingType);
    }

    @Test
    public void shouldReturnNoneWhenSortTypeIdIsHighestValidTest() {
        // Arrange
        int sortTypeId = SearchSortingType.values().length;

        // Act
        SearchSortingType sortingType = inputValidate.validateInputSortType(sortTypeId);

        // Assert
        assertEquals(SearchSortingType.NONE, sortingType);
    }

    @Test
    public void shouldThrowSearchSortTypeIdNotValidExceptionWhenSortTypeIdIsZeroTest() {
        // Arrange
        int sortTypeId = 0;

        // Act
        Executable throwAct = () -> inputValidate.validateInputSortType(sortTypeId);

        // Assert
        assertThrows(SearchSortTypeIdNotValidException.class, throwAct);
    }

    @Test
    public void shouldThrowSearchSortTypeIdNotValidExceptionWhenSortTypeIdIsAboveHighestTest() {
        // Arrange
        int sortTypeId = SearchSortingType.values().length + 1;

        // Act
        Executable throwAct = () -> inputValidate.validateInputSortType(sortTypeId);

        // Assert
        assertThrows(SearchSortTypeIdNotValidException.class, throwAct);
    }

}
