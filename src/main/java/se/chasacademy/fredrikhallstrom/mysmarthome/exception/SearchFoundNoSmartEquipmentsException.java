package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class SearchFoundNoSmartEquipmentsException extends RuntimeException {
    public SearchFoundNoSmartEquipmentsException(String searchTerm) {
        String message = String.format("Can't find any SmartEquipment that contains %s in name", searchTerm);
        super(message);
    }
}
