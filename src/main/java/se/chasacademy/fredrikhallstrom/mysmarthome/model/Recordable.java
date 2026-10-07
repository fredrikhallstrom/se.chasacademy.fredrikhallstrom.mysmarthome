package se.chasacademy.fredrikhallstrom.mysmarthome.model;

public interface Recordable {

    String getName();

    default String recordOn() {
        return String.format("Camera, %s is recording", getName());
    }

    default String recordOff() {
        return String.format("Camera, %s is not recording", getName());
    }

}
