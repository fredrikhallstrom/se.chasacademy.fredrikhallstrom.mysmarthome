package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.Clock;

public class Lamp extends SmartEquipment implements Lightable {
    public Lamp(
            String name,
            Clock clock) {
        super(name, clock);
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
