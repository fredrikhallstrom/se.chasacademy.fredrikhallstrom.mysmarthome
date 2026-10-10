package se.chasacademy.fredrikhallstrom.mysmarthome.model;

interface Recordable {

    default String recordOn() {
        return "recording is on";
    }

    default String recordOff() {
        return "recording is off";
    }

}
