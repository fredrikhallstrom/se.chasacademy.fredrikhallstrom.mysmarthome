package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class FoundNoSmartEquipmentByIdException extends RuntimeException {
    public FoundNoSmartEquipmentByIdException(int searchId) {
        String message = String.format("Can't find any SmartEquipment that has Id: %d", searchId);
        super(message);
    }
}
