package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentlist;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;

import java.util.LinkedHashMap;

public class MenuSmartEquipmentList extends BaseForMenu {
    SmartEquipmentService smartEquipmentService;

    public MenuSmartEquipmentList(SmartEquipmentService smartEquipmentService) {
        this.smartEquipmentService = smartEquipmentService;
    }

    public void menu() {
        menuItemsMap = new LinkedHashMap<>();

        menuItemsMap.put("List All SmartEquipment in order of Current State", this::listSmartEquipmentByState);

        super.menu(menuItemsMap);

    }

    private void listSmartEquipmentByState() {
        try {
            for (SmartEquipment smartEquipment : smartEquipmentService.AllSmartEquipmentAsList()) {
                IO.println(smartEquipment);
            }
        } catch (SmartEquipmentInRepositoryNotFoundException e) {
            IO.println(e.getMessage());
        }
    }

}
