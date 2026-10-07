package se.chasacademy.fredrikhallstrom.mysmarthome.model;

public interface Heatable {

    String getName();

    default String heatOn() {
        return String.format("Heater, %s is now heating", getName());

    }

    default String heatOff() {
        return String.format("Heater, %s is not heating", getName());
    }

}
