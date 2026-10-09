package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class SmartEquipmentIdCantBeNegative extends RuntimeException {
    public SmartEquipmentIdCantBeNegative(int assignedId) {
        String message = "Id can't be negative: " + assignedId;
        super(message);
    }
}
