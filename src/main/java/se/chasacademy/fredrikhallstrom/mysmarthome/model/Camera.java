package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.Clock;
import java.time.LocalDateTime;

public class Camera extends SmartEquipment implements Recordable {
      public Camera(
            String name,
            LocalDateTime installationTime, Clock clock) {
        super(name, installationTime, clock);
    }

    @Override
    public String setStateActive() {
        super.setStateActive();
        return recordOn();
    }

    @Override
    public String setStateInactive() {
        super.setStateInactive();
        return recordOff();
    }

}
