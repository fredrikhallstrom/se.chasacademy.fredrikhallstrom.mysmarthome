package se.chasacademy.fredrikhallstrom.mysmarthome.model;

public interface Heatable {
    String name = null;

    default void heatOn() {
        IO.println(String.format("Heating source %s is now heating", name));
    }

    default void heatOff() {
        IO.println(String.format("Heating source %s is not heating", name));
    }

}
