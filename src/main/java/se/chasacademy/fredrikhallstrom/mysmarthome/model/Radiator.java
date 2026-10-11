package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;
import java.util.List;

public class Radiator extends SmartEquipment implements Heatable {
    public Radiator(
            String name,
            LocalDateTime installationTime) {
        super(name, installationTime);
    }

    @Override
    public List<String> getStateMessages() {
        if (isStateActive) {
            return List.of(heatOn());
        } else {
            return List.of(heatOff());
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
