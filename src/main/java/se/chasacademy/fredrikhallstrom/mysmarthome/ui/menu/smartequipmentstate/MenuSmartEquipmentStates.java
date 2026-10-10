package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentstate;

import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;

import java.util.LinkedHashMap;

public class MenuSmartEquipmentStates extends BaseForMenu {
    private final OptionSmartEquipmentShowState optionSmartEquipmentShowState;
    private final OptionSmartEquipmentStateChange optionSmartEquipmentStateChange;

    public MenuSmartEquipmentStates(SmartEquipmentHandlerService smartEquipmentHandlerService) {
        OptionSmartEquipmentStatesHelper optionSmartEquipmentStatesHelper = new OptionSmartEquipmentStatesHelper(
                smartEquipmentHandlerService
        );
        this.optionSmartEquipmentShowState = new OptionSmartEquipmentShowState(
                smartEquipmentHandlerService,
                optionSmartEquipmentStatesHelper
        );
        this.optionSmartEquipmentStateChange = new OptionSmartEquipmentStateChange(
                smartEquipmentHandlerService,
                optionSmartEquipmentStatesHelper
        );

    }

    @Override
    public void menu() {
        menuItemsMap = new LinkedHashMap<>();
        menuItemsMap.put("SmartEquipment State Menu", () -> {
        });

        menuItemsMap.put("Show Smart Equipment State",
                optionSmartEquipmentShowState::showSmartEquipmentState
        );
        menuItemsMap.put("Change Smart Equipment State",
                optionSmartEquipmentStateChange::changeSmartEquipmentState
        );

        super.menu(menuItemsMap);
    }


}

