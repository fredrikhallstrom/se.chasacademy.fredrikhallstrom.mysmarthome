package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public class Lamp extends SmartEquipment implements Lightable {
    private String name;
    private final LocalDateTime installationTime;
    private LocalDateTime lastActivationTime;
    private LocalDateTime lastDeactivationTime;
    private boolean isStateActive = false;


    public Lamp(
            String name,
            LocalDateTime installationTime) {

        this.name = name;
        this.installationTime = installationTime;

        super(name, installationTime);
    }

    @Override
    public void setStateActive() {
        super.setStateActive();
        lightOn();
    }

    @Override
    public void setStateInactive() {
        super.setStateInactive();
        lightOff();
    }

}
