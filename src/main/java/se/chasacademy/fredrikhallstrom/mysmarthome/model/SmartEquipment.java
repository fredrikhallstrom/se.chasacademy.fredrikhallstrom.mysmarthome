package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;

import java.time.LocalDateTime;

public abstract class SmartEquipment {
    private static int uniqueIdCounter = 1000;
    protected int id;
    protected String name;
    protected LocalDateTime installationTime;
    protected LocalDateTime lastActivationTime;
    protected LocalDateTime lastDeactivationTime;
    protected boolean isStateActive = false;

    protected SmartEquipment(String name, LocalDateTime installationTime) {
        this.id = uniqueIdCounter;
        this.name = name;
        this.installationTime = installationTime;
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
        if (isStateActive) {
            throw new CantActiveAlreadyActivatedEquipmentException();
        }

       isStateActive = true;
    }

    public void setStateInactive() {
        if (!isStateActive) {
            throw new CantDeactivateAlreadyDeactivatedEquipmentException();
        }

        isStateActive = false;
    }

}
