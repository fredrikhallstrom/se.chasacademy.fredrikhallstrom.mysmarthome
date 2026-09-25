package model;

import java.time.LocalDateTime;

public class SmartEquipment {
    private static int uniqueIdCounter = 1000;
    private int id;
    private String name;
    private LocalDateTime installationTime;
    private LocalDateTime lastActivationTime;
    private LocalDateTime lastDeactivationTime;
    private boolean isStateActive;

    private SmartEquipment(String name, LocalDateTime installationTime, LocalDateTime lastActivationTime, LocalDateTime lastDeactivationTime, boolean isStateActive) {
        this.id = uniqueIdCounter;
        this.name = name;
        this.installationTime = installationTime;
        this.lastActivationTime = lastActivationTime;
        this.lastDeactivationTime = lastDeactivationTime;
        this.isStateActive = isStateActive;
        uniqueIdCounter++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getInstallationTime() {
        return installationTime;
    }

    public LocalDateTime getLastActivationTime() {
        return lastActivationTime;
    }

    public LocalDateTime getLastDeactivationTime() {
        return lastDeactivationTime;
    }

    public boolean isStateActive() {
        return isStateActive;
    }

    public void setStateActive() {
        isStateActive = true;
    }

    public void setStateInactive() {
        isStateActive = false;
    }

}
