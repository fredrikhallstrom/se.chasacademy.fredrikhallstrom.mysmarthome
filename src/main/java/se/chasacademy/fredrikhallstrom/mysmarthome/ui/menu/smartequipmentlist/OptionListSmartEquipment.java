package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentlist;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentNotFoundInRepositoryException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentServiceInterface;

public class OptionListSmartEquipment {
    private final SmartEquipmentServiceInterface smartEquipmentService;

    OptionListSmartEquipment(SmartEquipmentServiceInterface smartEquipmentService) {
        this.smartEquipmentService = smartEquipmentService;
    }

    void listSmartEquipmentSortedById() {
        try {
            for (SmartEquipment smartEquipment : smartEquipmentService.getAllSmartEquipmentsSortedMapById().values()) {
                IO.println(smartEquipment);
            }
            IO.println("");
        } catch (SmartEquipmentNotFoundInRepositoryException e) {
            IO.println(e.getMessage());
        }
    }

}
