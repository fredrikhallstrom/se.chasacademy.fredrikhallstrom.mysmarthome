package se.chasacademy.fredrikhallstrom.mysmarthome.service.handler;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

public class SmartEquipmentHandlerService {
    public SmartEquipmentHandlerService() {}

    public String setSmartEquipmentState(SmartEquipment smartEquipment, boolean turnStateOn)
            throws
            CantActiveAlreadyActivatedEquipmentException,
            CantDeactivateAlreadyDeactivatedEquipmentException {

        if (turnStateOn) {
            return smartEquipment.setStateActive();

        } else {
            return smartEquipment.setStateInactive();

        }
    }

}
