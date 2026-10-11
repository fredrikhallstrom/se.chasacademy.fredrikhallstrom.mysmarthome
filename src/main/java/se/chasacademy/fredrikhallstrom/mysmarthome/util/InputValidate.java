package se.chasacademy.fredrikhallstrom.mysmarthome.util;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.StringCantHaveSpecialCharsOnlyLettersNumbersSpacesException;

public interface InputValidate {
    default int validateInputStringToInt(String stringToValidateAsInt)
            throws MustBeANumberException {

        try {
            return Integer.parseInt(stringToValidateAsInt);
        } catch (NumberFormatException e) {
            throw new MustBeANumberException();
        }
    }

    default String validateInputStringNoSpecialChars(String stringToValidate)
            throws StringCantHaveSpecialCharsOnlyLettersNumbersSpacesException {

        if (!stringToValidate.matches("[a-zA-Z0-9 ]*")) {
            throw new StringCantHaveSpecialCharsOnlyLettersNumbersSpacesException();
        }
        return stringToValidate.trim();
    }
}
