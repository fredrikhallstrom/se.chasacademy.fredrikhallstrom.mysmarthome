package se.chasacademy.fredrikhallstrom.mysmarthome.exception.ui;

public class MenuBuildErrorException extends RuntimeException {
    public MenuBuildErrorException(String bugMessage) {
        String message = String.format("Software bug detected, report to developer: %s", bugMessage);
        super(message);
    }
}
