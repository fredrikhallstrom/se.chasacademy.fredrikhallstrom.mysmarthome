package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentsearch;

import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SmartEquipmentSearchService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;

import java.util.LinkedHashMap;

public class MenuSmartEquipmentSearch extends BaseForMenu {
    private final OptionSearchSmartEquipment optionSearch;

    public MenuSmartEquipmentSearch(SmartEquipmentSearchService smartEquipmentSearchService) {
        this.optionSearch = new OptionSearchSmartEquipment(smartEquipmentSearchService);
    }

    @Override
    public void menu() {
        menuItemsMap = new LinkedHashMap<>();

        menuItemsMap.put("Search for smart equipment menu", () -> {});
        menuItemsMap.put("By name", optionSearch::searchByName);
        menuItemsMap.put("By id", optionSearch::searchById);

        super.menu(menuItemsMap);
    }
}
