package se.chasacademy.fredrikhallstrom.mysmarthome.model;

public interface Recordable {
    String name = null;

    default String recordOn() {
        return String.format("Camera %s is recording", name);
    }

    default String recordOff() {
        return String.format("Camera %s is not recording", name);
    }

}
