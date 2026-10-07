package se.chasacademy.fredrikhallstrom.mysmarthome.model;

public interface Lightable {

    String getName();

    default String lightOn() {
        return String.format("Lamp %s is on", getName());
    }

    default String lightOff() {
        return String.format("Lamp %s is off", getName());
    }

}
