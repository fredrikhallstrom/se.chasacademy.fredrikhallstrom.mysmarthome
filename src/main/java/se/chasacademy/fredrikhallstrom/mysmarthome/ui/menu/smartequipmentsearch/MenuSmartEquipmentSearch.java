package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentsearch;

import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SmartEquipmentSearchService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;

import java.util.HashMap;

public class MenuSmartEquipmentSearch extends BaseForMenu {
    private final OptionSearch optionSearch;

    public MenuSmartEquipmentSearch(SmartEquipmentSearchService smartEquipmentSearchService) {
        this.optionSearch = new OptionSearch(smartEquipmentSearchService);
    }

    @Override
    public void menu() {
        menuItemsMap = new HashMap<>();

        menuItemsMap.put("Search for smart equipment menu", () -> {});
        menuItemsMap.put("By name", optionSearch::searchByName);
        menuItemsMap.put("By type", optionSearch::searchByType);

        super.menu(menuItemsMap);
    }
}
