package se.chasacademy.fredrikhallstrom.mysmarthome.service.handler;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

import java.time.Clock;
import java.time.LocalDateTime;

public class SmartEquipmentHandlerService {
    Clock clock;

    public SmartEquipmentHandlerService(Clock clock) {
        this.clock = clock;
    }

    public String setSmartEquipmentState(SmartEquipment smartEquipment, boolean turnStateOn)
            throws
            CantActiveAlreadyActivatedEquipmentException,
            CantDeactivateAlreadyDeactivatedEquipmentException {

        if (turnStateOn) {
            return smartEquipment.setStateActive(LocalDateTime.now(clock));

        } else {
            return smartEquipment.setStateInactive(LocalDateTime.now(clock));

        }
    }

    public String getSmartEquipmentState(SmartEquipment smartEquipment) {
        return smartEquipment.getStateString();

    }

}
