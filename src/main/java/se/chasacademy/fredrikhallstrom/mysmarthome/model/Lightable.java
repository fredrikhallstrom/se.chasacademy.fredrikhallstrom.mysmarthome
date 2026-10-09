package se.chasacademy.fredrikhallstrom.mysmarthome.model;

interface Lightable {

    String getName();

    default String lightOn() {
        return String.format("%s, lamp is on", getName());
    }

    default String lightOff() {
        return String.format("%s, lamp is off", getName());
    }

}
