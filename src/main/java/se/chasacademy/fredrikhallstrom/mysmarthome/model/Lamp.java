package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public class Lamp extends SmartEquipment implements Lightable {
    public Lamp(
            String name,
            LocalDateTime installationTime) {
        super(name, installationTime);
    }

    @Override
    public String getStateString() {
        if (isStateActive) {
            return lightOn();
        } else {
            return lightOff();
        }
    }

    @Override
    public String setStateActive(LocalDateTime activationTime) {
        super.setStateActive(activationTime);
        return lightOn();
    }

    @Override
    public String setStateInactive(LocalDateTime deActivationTime) {
        super.setStateInactive(deActivationTime);
        return lightOff();
    }

}
