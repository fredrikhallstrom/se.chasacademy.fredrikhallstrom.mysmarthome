package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public class Radiator extends SmartEquipment implements Heatable {
    public Radiator(
            String name,
            LocalDateTime installationTime) {
        super(name, installationTime);
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
    public String setStateActive(LocalDateTime activationTime) {
        super.setStateActive(activationTime);
        return heatOn();
    }

    @Override
    public String setStateInactive(LocalDateTime deactivationTime) {
        super.setStateInactive(deactivationTime);
        return heatOff();
    }

}
