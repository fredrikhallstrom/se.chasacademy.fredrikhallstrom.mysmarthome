package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.Clock;
import java.time.LocalDateTime;

public class Lamp extends SmartEquipment implements Lightable {
    public Lamp(
            String name,
            LocalDateTime installationTime,
            Clock clock) {
        super(name, installationTime, clock);
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
    public String setStateActive() {
        super.setStateActive();
        return lightOn();
    }

    @Override
    public String setStateInactive() {
        super.setStateInactive();
        return lightOff();
    }

}
