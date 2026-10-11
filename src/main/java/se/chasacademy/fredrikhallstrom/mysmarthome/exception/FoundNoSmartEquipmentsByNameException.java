package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class FoundNoSmartEquipmentsByNameException extends RuntimeException {
    public FoundNoSmartEquipmentsByNameException(String searchTerm) {
        String message = String.format("Can't find any SmartEquipment that contains %s in name", searchTerm);
        super(message);
    }
}
