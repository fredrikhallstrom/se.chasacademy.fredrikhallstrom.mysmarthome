package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public class Camera extends SmartEquipment implements Recordable {
      public Camera(
            String name,
            LocalDateTime installationTime) {
        super(name, installationTime);
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
