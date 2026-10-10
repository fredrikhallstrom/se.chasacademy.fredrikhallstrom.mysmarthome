package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class CantDeactivateAlreadyDeactivatedStateException extends RuntimeException {
    public CantDeactivateAlreadyDeactivatedStateException() {
        String message = "Can't deactivate already deactivated state";
        super(message);
    }
}
