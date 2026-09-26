package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public class Lamp extends SmartEquipment {
    private String lampName;
    private LocalDateTime installationTime;
    private LocalDateTime lastActivationTime;
    private LocalDateTime lastDeactivationTime;
    private boolean isStateActive = false;

    public Lamp(
            String lampName,
            LocalDateTime installationTime) {

        this.lampName = lampName;
        this.installationTime = installationTime;

        super(lampName, installationTime);
    }
}
