package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class SmartEquipmentHasNoExtraStateException extends RuntimeException {
    public SmartEquipmentHasNoExtraStateException() {
        String message = "Smart equipment has no extra state";
        super(message);
    }
}
