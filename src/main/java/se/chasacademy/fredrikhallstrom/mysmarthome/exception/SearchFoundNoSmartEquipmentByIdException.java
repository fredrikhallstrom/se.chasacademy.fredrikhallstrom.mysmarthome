package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class SearchFoundNoSmartEquipmentByIdException extends RuntimeException {
    public SearchFoundNoSmartEquipmentByIdException(int searchId) {
        String message = String.format("Can't find any SmartEquipment that has Id: %d", searchId);
        super(message);
    }
}
