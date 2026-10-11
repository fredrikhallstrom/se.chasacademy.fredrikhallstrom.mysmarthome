package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentsearch;

import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SmartEquipmentSearchService;
import se.chasacademy.fredrikhallstrom.mysmarthome.util.InputValidate;

public class OptionSearch implements InputValidate {
    private final SmartEquipmentSearchService smartEquipmentSearchService;

    public OptionSearch(SmartEquipmentSearchService smartEquipmentSearchService) {
        this.smartEquipmentSearchService = smartEquipmentSearchService;
    }

    public void searchByName() {
        IO.print("Search by name");

        IO.print("Enter name: ");
        String name = IO.readln();
        IO.print("Search results:");
        IO.print("No results found");
    }

    public void searchByType() {
        IO.print("Search by type");
        IO.print("Enter type: ");
        String type = IO.readln();
        IO.print("Search results:");
        IO.print("No results found");
    }
}
