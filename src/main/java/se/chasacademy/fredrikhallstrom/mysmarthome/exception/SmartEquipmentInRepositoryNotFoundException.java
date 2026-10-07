package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class SmartEquipmentInRepositoryNotFoundException extends RuntimeException {
    public SmartEquipmentInRepositoryNotFoundException() {
        String message = "No SmartEquipment found in Repository";
        super(message);
    }
}
