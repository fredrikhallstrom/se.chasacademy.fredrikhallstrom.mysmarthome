package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.Clock;

public class Radiator extends SmartEquipment implements Heatable {
    public Radiator(
            String name,
            Clock clock) {
        super(name, clock);
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
