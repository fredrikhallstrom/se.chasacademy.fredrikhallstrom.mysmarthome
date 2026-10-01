package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public class Lamp extends SmartEquipment implements Lightable {
    public Lamp(
            String name,
            LocalDateTime installationTime) {
        super(name, installationTime);
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
