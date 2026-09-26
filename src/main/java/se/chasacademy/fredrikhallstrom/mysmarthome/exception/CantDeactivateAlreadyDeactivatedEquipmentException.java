package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class CantDeactivateAlreadyDeactivatedEquipmentException extends RuntimeException {
    public CantDeactivateAlreadyDeactivatedEquipmentException() {
        String message = "Can't deactivate already deactivated equipment";
        super(message);
    }
}
