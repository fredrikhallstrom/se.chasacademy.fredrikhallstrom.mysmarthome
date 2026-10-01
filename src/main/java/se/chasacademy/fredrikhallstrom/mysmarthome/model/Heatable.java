package se.chasacademy.fredrikhallstrom.mysmarthome.model;

public interface Heatable {
    String name = null;

    default String heatOn() {
        return String.format("Heating source %s is now heating", name);

    }

    default String heatOff() {
        return String.format("Heating source %s is not heating", name);
    }

}
