package se.chasacademy.fredrikhallstrom.mysmarthome.model;

interface Heatable {

    default String heatOn() {
        return "heating is on";
    }

    default String heatOff() {
        return "heating is off";
    }

}
