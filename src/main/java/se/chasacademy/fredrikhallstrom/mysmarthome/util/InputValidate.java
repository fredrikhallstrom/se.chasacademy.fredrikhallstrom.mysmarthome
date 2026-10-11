package se.chasacademy.fredrikhallstrom.mysmarthome.util;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchSortTypeIdNotValidException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.StringCantHaveSpecialCharsOnlyLettersNumbersSpacesException;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SearchSortingType;

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


    default SearchSortingType validateInputSortType(int searchSortTypeId)
            throws SearchSortTypeIdNotValidException {

        if (searchSortTypeId < 1 || searchSortTypeId > SearchSortingType.values().length) {
            throw new SearchSortTypeIdNotValidException();
        }
        return SearchSortingType.values()[searchSortTypeId - 1];
    }
}
