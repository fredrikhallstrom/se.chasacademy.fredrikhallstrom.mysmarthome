package se.chasacademy.fredrikhallstrom.mysmarthome.model;

public interface Recordable {
    String name = null;

    default void recordOn() {
        IO.println(String.format("Camera %s is recording", name));
    }

    default void recordOff() {
        IO.println(String.format("Camera %s is not recording", name));
    }

}
