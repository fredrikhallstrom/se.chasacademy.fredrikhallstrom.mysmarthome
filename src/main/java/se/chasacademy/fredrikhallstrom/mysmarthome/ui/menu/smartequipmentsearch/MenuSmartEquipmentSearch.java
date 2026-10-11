package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentsearch;

import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SmartEquipmentSearchService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;

import java.util.HashMap;

public class MenuSmartEquipmentSearch extends BaseForMenu {
    private final SmartEquipmentSearchService smartEquipmentSearchService;

    public MenuSmartEquipmentSearch(SmartEquipmentSearchService smartEquipmentSearchService) {
        this.smartEquipmentSearchService = smartEquipmentSearchService;
    }

    @Override
    public void menu() {
        menuItemsMap = new HashMap<>();

        menuItemsMap.put("Search for smart equipment menu", () -> {});
        menuItemsMap.put("By name", OptionSearch::searchByName);
        menuItemsMap.put("By type", OptionSearch::searchByType);

        super.menu(menuItemsMap);
    }
}
