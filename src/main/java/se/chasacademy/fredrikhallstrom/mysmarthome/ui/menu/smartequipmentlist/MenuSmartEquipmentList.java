package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentlist;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentNotFoundException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;

import java.util.LinkedHashMap;

public class MenuSmartEquipmentList extends BaseForMenu {
    private final OptionListSmartEquipment optionListSmartEquipment;

    public MenuSmartEquipmentList(SmartEquipmentService smartEquipmentService) {
        this.optionListSmartEquipment = new OptionListSmartEquipment(smartEquipmentService);
    }

    public void menu() {
        menuItemsMap = new LinkedHashMap<>();

        menuItemsMap.put("List All SmartEquipment Menu", () -> {
        });

        menuItemsMap.put("List All SmartEquipment in order of Current State",
                optionListSmartEquipment::listSmartEquipmentByState);

        super.menu(menuItemsMap);

    }



}
