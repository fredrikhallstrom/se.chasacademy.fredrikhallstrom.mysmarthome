package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class CantActiveAlreadyActivatedEquipmentException extends RuntimeException {
    public CantActiveAlreadyActivatedEquipmentException() {
        String message = "Can't activate equipment that already activated";
        super(message);
    }
}
