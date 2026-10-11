package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentaddremove;

import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentServiceInterface;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;

import java.util.LinkedHashMap;

public class MenuSmartEquipmentAddRemove extends BaseForMenu {
    private final OptionRemoveSmartEquipment optionRemoveSmartEquipment;
    private final OptionAddSmartEquipment optionAddSmartEquipment;

    public MenuSmartEquipmentAddRemove(SmartEquipmentServiceInterface smartEquipmentService) {
        this.optionRemoveSmartEquipment = new OptionRemoveSmartEquipment(smartEquipmentService);
        this.optionAddSmartEquipment = new OptionAddSmartEquipment(smartEquipmentService);

    }

    @Override
    public void menu() {
        menuItemsMap = new LinkedHashMap<>();
        menuItemsMap.put("SmartEquipment Add/Remove Menu", () -> {
        });

        menuItemsMap.put("Add Smart Equipment",
                optionAddSmartEquipment::addSmartEquipment
        );
        menuItemsMap.put("Remove Smart Equipment",
                optionRemoveSmartEquipment::removeSmartEquipment
        );

        super.menu(menuItemsMap);
    }
}
