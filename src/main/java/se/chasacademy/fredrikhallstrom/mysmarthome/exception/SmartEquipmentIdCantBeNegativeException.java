package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class SmartEquipmentIdCantBeNegativeException extends RuntimeException {
    public SmartEquipmentIdCantBeNegativeException(int assignedId) {
        String message = "Id can't be negative: " + assignedId;
        super(message);
    }
}
