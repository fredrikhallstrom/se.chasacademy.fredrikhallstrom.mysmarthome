package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class StringCantHaveSpecialCharsOnlyLettersNumbersSpacesException extends RuntimeException {
    public StringCantHaveSpecialCharsOnlyLettersNumbersSpacesException() {
        String message = "String can't have special characters, only letters, numbers and spaces";
        super(message);
    }
}
