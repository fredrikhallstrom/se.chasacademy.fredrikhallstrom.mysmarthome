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
    public void setStateActive() {
        super.setStateActive();
        recordOn();
    }

    @Override
    public void setStateInactive() {
        super.setStateInactive();
        recordOff();
    }

}
