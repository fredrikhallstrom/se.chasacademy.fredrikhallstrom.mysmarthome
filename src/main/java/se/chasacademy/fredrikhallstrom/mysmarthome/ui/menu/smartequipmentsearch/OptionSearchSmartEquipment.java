package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentsearch;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.FoundNoSmartEquipmentsByNameException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.FoundNoSmartEquipmentByIdException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchSortTypeIdNotValidException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentNotFoundInRepositoryException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.StringCantHaveSpecialCharsOnlyLettersNumbersSpacesException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SearchSortingType;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SmartEquipmentSearchService;
import se.chasacademy.fredrikhallstrom.mysmarthome.util.InputValidate;

import java.util.List;

public class OptionSearchSmartEquipment implements InputValidate {
    private final SmartEquipmentSearchService smartEquipmentSearchService;

    private record SearchHelperResponse(String name, SearchSortingType searchSortingType) {}

    OptionSearchSmartEquipment(SmartEquipmentSearchService smartEquipmentSearchService) {
        this.smartEquipmentSearchService = smartEquipmentSearchService;
    }

    void searchByName() {
        SearchByMessageTypes searchByMessage = SearchByMessageTypes.NAME;

        try {
            SearchHelperResponse searchHelperResponse = searchByHelper(searchByMessage);
            IO.println("");
            List<SmartEquipment> smartEquipmentSearchResults = smartEquipmentSearchService.searchSmartEquipmentByName(
                    searchHelperResponse.name(),
                    searchHelperResponse.searchSortingType()
            );

            IO.println("Search results:");
            IO.println("");
            for (SmartEquipment smartEquipment : smartEquipmentSearchResults) {
                IO.println(smartEquipmentSearchService.getSmartEquipmentToString(smartEquipment));
            }

        } catch (FoundNoSmartEquipmentsByNameException |
                 StringCantHaveSpecialCharsOnlyLettersNumbersSpacesException |
                 SmartEquipmentNotFoundInRepositoryException |
                 MustBeANumberException |
                 SearchSortTypeIdNotValidException error) {

            IO.println(error.getMessage());
        }
        IO.println("");
    }

    void searchById() {
        try {
            IO.println("Search by id");
            int id = validateInputStringToInt(IO.readln("Enter id: "));
            IO.println("");

            SmartEquipment found = smartEquipmentSearchService.searchSmartEquipmentById(id);

            IO.println("Search result:");
            IO.println("");
            IO.println(found.toString());

        } catch (MustBeANumberException |
                 FoundNoSmartEquipmentByIdException |
                 SmartEquipmentNotFoundInRepositoryException error) {

            IO.println(error.getMessage());
        }
        IO.println("");
    }


    private SearchHelperResponse searchByHelper(SearchByMessageTypes searchByMessageType) {
        IO.println(String.format("Search by %s", searchByMessageType.getFriendlyName()));

        IO.println(String.format("Enter %s to Search for: ", searchByMessageType.getFriendlyName()));

        String validatedString = validateInputStringNoSpecialChars(IO.readln());

        IO.println(String.format("\nSort %s with order:", searchByMessageType.getFriendlyName()));

        List<SearchSortingType> sortingTypes = smartEquipmentSearchService.getSortTypes();

        int index = 1;
        for (SearchSortingType sortingType : sortingTypes) {
            IO.print(String.format("%d. %s\n", index, sortingType.getFriendlyName()));
            index++;
        }

        return new SearchHelperResponse(
                validatedString,
                validateInputSortType(validateInputStringToInt(IO.readln("Choice: "))));
    }


}
