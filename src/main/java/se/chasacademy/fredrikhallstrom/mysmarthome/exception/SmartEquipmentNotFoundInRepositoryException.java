package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class SmartEquipmentNotFoundInRepositoryException extends RuntimeException {
    public SmartEquipmentNotFoundInRepositoryException() {
        String message = "No SmartEquipment found!!";
        super(message);
    }
}
