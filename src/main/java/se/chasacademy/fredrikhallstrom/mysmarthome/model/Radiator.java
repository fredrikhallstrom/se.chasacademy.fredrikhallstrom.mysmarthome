package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.Clock;
import java.time.LocalDateTime;

public class Radiator extends SmartEquipment implements Heatable {
    public Radiator(
            String name,
            LocalDateTime installationTime,
            Clock clock) {
        super(name, installationTime, clock);
    }

    @Override
    public String getStateString() {
        if (isStateActive) {
            return heatOn();
        } else {
            return heatOff();
        }
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
