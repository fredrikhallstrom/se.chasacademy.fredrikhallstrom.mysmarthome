package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu;

import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SmartEquipmentSearchService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.demonstration.MenuDemonstration;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentaddremove.MenuSmartEquipmentAddRemove;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentlist.MenuSmartEquipmentList;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentsearch.MenuSmartEquipmentSearch;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentstates.MenuSmartEquipmentShowState;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentstates.MenuSmartEquipmentStateChange;

import java.util.LinkedHashMap;

public class MenuMain extends BaseForMenu {
    public MenuSmartEquipmentAddRemove menuSmartEquipmentAddRemove;
    public MenuSmartEquipmentList menuSmartEquipmentList;
    public MenuSmartEquipmentSearch menuSmartEquipmentSearch;
    public MenuDemonstration menuDemonstration = new MenuDemonstration();
    public MenuSmartEquipmentStateChange menuSmartEquipmentStateChange;
    public MenuSmartEquipmentShowState menuSmartEquipmentShowState;

    public MenuMain(
            SmartEquipmentService smartEquipmentService,
            SmartEquipmentHandlerService smartEquipmentHandlerService,
            SmartEquipmentSearchService smartEquipmentSearchService)
    {
        this.menuSmartEquipmentAddRemove = new MenuSmartEquipmentAddRemove(smartEquipmentService);
        this.menuSmartEquipmentList = new MenuSmartEquipmentList(smartEquipmentService);
        this.menuSmartEquipmentShowState = new MenuSmartEquipmentShowState(smartEquipmentHandlerService);
        this.menuSmartEquipmentStateChange = new MenuSmartEquipmentStateChange(smartEquipmentHandlerService);
        this.menuSmartEquipmentSearch = new MenuSmartEquipmentSearch(smartEquipmentSearchService);

        super();
    }
    @Override
    public void menu() {
        menuItemsMap = new LinkedHashMap<>();

            // MenuItems
            menuItemsMap.put("Main menu", () -> {});
            menuItemsMap.put("Add/Remove SmartEquipment", () -> menuSmartEquipmentAddRemove.menu());
            menuItemsMap.put("Search SmartEquipment", () -> menuSmartEquipmentSearch.menu());
            menuItemsMap.put("List All SmartEquipment", () -> menuSmartEquipmentList.menu());
            menuItemsMap.put("Change SmartEquipment State", () -> menuSmartEquipmentStateChange.menu());
            menuItemsMap.put("Show SmartEquipment State", () -> menuSmartEquipmentShowState.menu());
            menuItemsMap.put("Demonstration", () -> menuDemonstration.menu());

    super.menu(menuItemsMap);
    }



}

