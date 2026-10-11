package se.chasacademy.fredrikhallstrom.mysmarthome.util;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;

public interface InputValidate {
    default int validateInputStringToInt(String stringToValidateAsInt)
            throws MustBeANumberException {

        try {
            return Integer.parseInt(stringToValidateAsInt);
        } catch (NumberFormatException e) {
            throw new MustBeANumberException();
        }
    }

    default String validateInputStringNoSpecialChars(String stringToValidate) {
        String validatedString = stringToValidate;
        return validatedString;
    }
}
