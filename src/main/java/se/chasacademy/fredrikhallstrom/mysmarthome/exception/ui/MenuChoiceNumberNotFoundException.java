package se.chasacademy.fredrikhallstrom.mysmarthome.exception.ui;

public class MenuChoiceNumberNotFoundException extends RuntimeException {
    public MenuChoiceNumberNotFoundException() {
        String message = "Menu Choice not found!\n";
        super(message);
    }
}
