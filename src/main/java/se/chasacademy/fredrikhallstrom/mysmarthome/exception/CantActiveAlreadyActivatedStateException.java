package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class CantActiveAlreadyActivatedStateException extends RuntimeException {
    public CantActiveAlreadyActivatedStateException() {
        String message = "Can't activate already activated state";
        super(message);
    }
}
