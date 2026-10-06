package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class SearchFoundNoSmartEquipmentsByNameException extends RuntimeException {
    public SearchFoundNoSmartEquipmentsByNameException(String searchTerm) {
        String message = String.format("Can't find any SmartEquipment that contains %s in name", searchTerm);
        super(message);
    }
}
