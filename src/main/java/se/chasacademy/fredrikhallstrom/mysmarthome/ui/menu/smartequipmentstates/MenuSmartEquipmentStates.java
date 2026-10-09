package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentstates;

import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;

import java.util.LinkedHashMap;

public class MenuSmartEquipmentStates extends BaseForMenu {
    private final OptionSmartEquipmentShowState optionSmartEquipmentShowState;
    private final OptionSmartEquipmentStateChange optionSmartEquipmentStateChange;

    public MenuSmartEquipmentStates(SmartEquipmentHandlerService smartEquipmentHandlerService) {
        this.optionSmartEquipmentShowState = new OptionSmartEquipmentShowState(smartEquipmentHandlerService);
        this.optionSmartEquipmentStateChange = new OptionSmartEquipmentStateChange(smartEquipmentHandlerService);
    }

    @Override
    public void menu() {
        menuItemsMap = new LinkedHashMap<>();
            menuItemsMap.put("SmartEquipment State Menu", () -> {});

            menuItemsMap.put("Show Smart Equipment State",
                    optionSmartEquipmentShowState::showSmartEquipmentState
                    );
            menuItemsMap.put("Change Smart Equipment State",
                    optionSmartEquipmentStateChange::changeSmartEquipmentState
                    );

        super.menu(menuItemsMap);


    }
}

