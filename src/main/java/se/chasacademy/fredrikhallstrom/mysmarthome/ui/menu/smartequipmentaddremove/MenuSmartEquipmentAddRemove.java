package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentaddremove;

import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;

import java.util.LinkedHashMap;

public class MenuSmartEquipmentAddRemove extends BaseForMenu {
    SmartEquipmentService smartEquipmentService;
    OptionRemoveSmartEquipment optionRemoveSmartEquipment;
    OptionAddSmartEquipment optionAddSmartEquipment;

    public MenuSmartEquipmentAddRemove(SmartEquipmentService smartEquipmentService) {
        this.smartEquipmentService = smartEquipmentService;
        this.optionRemoveSmartEquipment = new OptionRemoveSmartEquipment(smartEquipmentService);
        this.optionAddSmartEquipment = new OptionAddSmartEquipment(smartEquipmentService);

    }

    public void menu() {
        menuItemsMap = new LinkedHashMap<>();

            menuItemsMap.put("Add Smart Equipment",
                    () -> optionAddSmartEquipment.addSmartEquipment(smartEquipmentService)
            );
            menuItemsMap.put("Remove Smart Equipment",
                    () -> optionRemoveSmartEquipment.removeSmartEquipment(smartEquipmentService)
            );
    }

}
