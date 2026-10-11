package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentlist;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentNotFoundException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;

public class OptionListSmartEquipment {
    private final SmartEquipmentService smartEquipmentService;

    OptionListSmartEquipment(SmartEquipmentService smartEquipmentService) {
        this.smartEquipmentService = smartEquipmentService;
    }

    void listSmartEquipmentByState() {
        try {
            for (SmartEquipment smartEquipment : smartEquipmentService.AllSmartEquipmentAsList()) {
                IO.println(smartEquipment);
            }
            IO.println("");
        } catch (SmartEquipmentNotFoundException e) {
            IO.println(e.getMessage());
        }
    }
}
