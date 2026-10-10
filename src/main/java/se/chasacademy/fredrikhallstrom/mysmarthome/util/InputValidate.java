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
}
