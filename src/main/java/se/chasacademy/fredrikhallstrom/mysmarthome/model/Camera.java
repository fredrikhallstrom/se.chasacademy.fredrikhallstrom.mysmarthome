package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public class Camera extends SmartEquipment implements Recordable {
    private String name;
    private final LocalDateTime installationTime;
    private LocalDateTime lastActivationTime;
    private LocalDateTime lastDeactivationTime;
    private boolean isStateActive = false;

    public Camera(
            String name,
            LocalDateTime installationTime) {

        this.name = name;
        this.installationTime = installationTime;

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
