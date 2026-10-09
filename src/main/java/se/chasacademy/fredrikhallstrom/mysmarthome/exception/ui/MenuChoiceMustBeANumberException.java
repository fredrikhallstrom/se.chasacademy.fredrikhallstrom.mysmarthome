package se.chasacademy.fredrikhallstrom.mysmarthome.exception.ui;

public class MenuChoiceMustBeANumberException extends RuntimeException {
    public MenuChoiceMustBeANumberException() {
        String message = "Choice must be a number!\n";
        super(message);
    }
}
