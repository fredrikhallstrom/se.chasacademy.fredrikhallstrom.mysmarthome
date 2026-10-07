package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

public class SmartEquipmentHandlerService {
    public SmartEquipmentHandlerService() {}

    public void setSmartEquipmentState(SmartEquipment smartEquipment, boolean turnStateOn)
            throws
            CantActiveAlreadyActivatedEquipmentException,
            CantDeactivateAlreadyDeactivatedEquipmentException {

        if (turnStateOn) {
            smartEquipment.setStateActive();

        } else {
            smartEquipment.setStateInactive();

        }
    }
}
