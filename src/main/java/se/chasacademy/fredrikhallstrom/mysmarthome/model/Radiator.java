package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public class Radiator extends SmartEquipment implements Heatable {
    public Radiator(
            String name,
            LocalDateTime installationTime) {
        super(name, installationTime);
    }

    @Override
    public String setStateActive() {
        super.setStateActive();
        return heatOn();
    }

    @Override
    public String setStateInactive() {
        super.setStateInactive();
        return heatOff();
    }

}
