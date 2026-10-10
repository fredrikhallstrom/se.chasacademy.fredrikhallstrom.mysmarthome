package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;
import java.util.List;

public class Lamp extends SmartEquipment implements Lightable {
    public Lamp(
            String name,
            LocalDateTime installationTime) {
        super(name, installationTime);
    }

    @Override
    public List<String> getStateMessages() {
        if (isStateActive) {
            return List.of(lightOn());
        } else {
            return List.of(lightOff());
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
