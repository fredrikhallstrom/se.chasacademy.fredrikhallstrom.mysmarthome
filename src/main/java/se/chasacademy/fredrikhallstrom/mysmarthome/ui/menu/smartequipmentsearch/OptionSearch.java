package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentsearch;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.StringCantHaveSpecialCharsOnlyLettersNumbersSpacesException;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SmartEquipmentSearchService;
import se.chasacademy.fredrikhallstrom.mysmarthome.util.InputValidate;

public class OptionSearch implements InputValidate {
    private final SmartEquipmentSearchService smartEquipmentSearchService;

    public OptionSearch(SmartEquipmentSearchService smartEquipmentSearchService) {
        this.smartEquipmentSearchService = smartEquipmentSearchService;
    }

    public void searchByName() {
        try {
            IO.println("Search by name");

            IO.println("Enter name to Search for: ");
            String validatedName = validateInputStringNoSpecialChars(IO.readln());
            IO.print("Search results:");
            IO.print("No results found");

        } catch (StringCantHaveSpecialCharsOnlyLettersNumbersSpacesException error) {
            IO.println(error.getMessage());
        }
    }

    public void searchByType() {
        IO.println("Search by type");
        IO.println("Enter type: ");
        String type = IO.readln();
        IO.println("Search results:");
        IO.println("No results found");
    }
}
