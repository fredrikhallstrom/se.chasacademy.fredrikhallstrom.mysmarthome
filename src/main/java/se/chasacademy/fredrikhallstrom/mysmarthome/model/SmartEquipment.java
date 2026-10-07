package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;

import java.time.Clock;
import java.time.LocalDateTime;

public abstract class SmartEquipment {
    protected int id = -1;
    protected String name;
    protected LocalDateTime installationTime;
    protected LocalDateTime lastActivationTime;
    protected LocalDateTime lastDeactivationTime;
    protected boolean isStateActive = false;
    private final Clock clock;

    protected SmartEquipment(String name, LocalDateTime installationTime, Clock clock) {
        this.name = name;
        this.installationTime = installationTime;
        this.clock = clock;
    }

    public String getName() {
        return name;
    }

    public abstract String getStateString();

    public LocalDateTime getInstallationTime() {
        return installationTime;
    }

    public LocalDateTime getLastActivationTime() {
        return lastActivationTime;
    }

    public LocalDateTime getLastDeactivationTime() {
        return lastDeactivationTime;
    }

    public int getId() {
        return id;
    }

    public boolean isStateActive() {
        return isStateActive;
    }

    public void setId(int assignedId) {
        if (id == -1) {
            this.id = assignedId;
        }
    }

    public String setStateActive() {
        if (isStateActive) {
            throw new CantActiveAlreadyActivatedEquipmentException();
        }

        isStateActive = true;
        this.lastActivationTime = LocalDateTime.now(clock);

        return "";
    }

    public String setStateInactive() {
        if (!isStateActive) {
            throw new CantDeactivateAlreadyDeactivatedEquipmentException();
        }

        isStateActive = false;
        this.lastDeactivationTime = LocalDateTime.now(clock);

        return "";
    }

    public String toString() {
        String statusMessageString = getStateString();

        return String.format("%s: %s", getName(), statusMessageString);
    }

}
