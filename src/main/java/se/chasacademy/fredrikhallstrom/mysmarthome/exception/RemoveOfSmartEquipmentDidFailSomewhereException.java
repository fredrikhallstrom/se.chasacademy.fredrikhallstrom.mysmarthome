package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

public class RemoveOfSmartEquipmentDidFailSomewhereException extends RuntimeException {
    public RemoveOfSmartEquipmentDidFailSomewhereException(SmartEquipment smartEquipment) {
        String message = String.format("Something went wrong when trying to remove %s", smartEquipment.getName());
        super(message);
    }
}
