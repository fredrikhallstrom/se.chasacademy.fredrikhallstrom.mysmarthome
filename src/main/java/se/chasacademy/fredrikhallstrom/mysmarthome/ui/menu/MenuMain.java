package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu;

import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SmartEquipmentSearchService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.demonstration.MenuDemonstration;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentaddremove.MenuSmartEquipmentAddRemove;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentlist.MenuSmartEquipmentList;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentsearch.MenuSmartEquipmentSearch;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentstate.MenuSmartEquipmentStates;

import java.util.LinkedHashMap;

public class MenuMain extends BaseForMenu {
    private final MenuSmartEquipmentAddRemove menuSmartEquipmentAddRemove;
    private final MenuSmartEquipmentList menuSmartEquipmentList;
    private final MenuSmartEquipmentSearch menuSmartEquipmentSearch;
    private final MenuDemonstration menuDemonstration = new MenuDemonstration();
    private final MenuSmartEquipmentStates menuSmartEquipmentStates;

    public MenuMain(
            SmartEquipmentService smartEquipmentService,
            SmartEquipmentHandlerService smartEquipmentHandlerService,
            SmartEquipmentSearchService smartEquipmentSearchService
    ) {
        this.menuSmartEquipmentAddRemove = new MenuSmartEquipmentAddRemove(smartEquipmentService);
        this.menuSmartEquipmentList = new MenuSmartEquipmentList(smartEquipmentService);
        this.menuSmartEquipmentStates = new MenuSmartEquipmentStates(smartEquipmentHandlerService);
        this.menuSmartEquipmentSearch = new MenuSmartEquipmentSearch(smartEquipmentSearchService);

        super();
    }

    @Override
    public void menu() {
        menuItemsMap = new LinkedHashMap<>();

        // MenuItems
        menuItemsMap.put("Main menu", () -> {
        });
        menuItemsMap.put("Add/Remove SmartEquipment", menuSmartEquipmentAddRemove::menu);
        menuItemsMap.put("Search SmartEquipment", menuSmartEquipmentSearch::menu);
        menuItemsMap.put("List All SmartEquipment", menuSmartEquipmentList::menu);
        menuItemsMap.put("SmartEquipment State Menu", menuSmartEquipmentStates::menu);
        menuItemsMap.put("Demonstration", menuDemonstration::menu);

        super.menu(menuItemsMap);
    }


}

