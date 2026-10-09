package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class MustBeANumberException extends RuntimeException {
    public MustBeANumberException() {
        String message = "Input must be a Number!\n";
        super(message);
    }
}
