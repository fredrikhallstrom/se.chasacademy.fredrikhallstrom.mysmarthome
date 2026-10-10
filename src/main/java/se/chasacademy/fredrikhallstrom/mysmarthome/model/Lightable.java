package se.chasacademy.fredrikhallstrom.mysmarthome.model;

interface Lightable {

    default String lightOn() {
        return "lamp is on";
    }

    default String lightOff() {
        return "lamp is off";
    }

}
