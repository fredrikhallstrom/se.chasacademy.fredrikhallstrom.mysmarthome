package se.chasacademy.fredrikhallstrom.mysmarthome.model;

public interface Lightable {
    String name = null;

    default void lightOn() {
        IO.println(String.format("Lamp %s is on", name));
    }

    default void lightOff() {
        IO.println(String.format("Lamp %s is off", name));
    }

}
