package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public class Radiator extends SmartEquipment{
    private String radiatorName;
    private LocalDateTime installationTime;
    private LocalDateTime lastActivationTime;
    private LocalDateTime lastDeactivationTime;
    private boolean isStateActive = false;

    public Radiator(
            String radiatorName,
            LocalDateTime installationTime) {

        this.radiatorName = radiatorName;
        this.installationTime = installationTime;

        super(radiatorName, installationTime);
    }
}
