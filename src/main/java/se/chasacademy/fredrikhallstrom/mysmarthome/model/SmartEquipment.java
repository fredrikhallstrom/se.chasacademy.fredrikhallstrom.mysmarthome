package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

import java.time.LocalDateTime;

public abstract class SmartEquipment {
    protected int id = -1;
    protected String name;
    protected LocalDateTime installationTime;
    protected LocalDateTime lastActivationTime;
    protected LocalDateTime lastDeactivationTime;
    protected boolean isStateActive = false;
    private SmartEquipmentRepository repository;

    protected SmartEquipment(String name, LocalDateTime installationTime) {
        this.name = name;
        this.installationTime = installationTime;

    }

    public String getName() {
        return name;
    }

    public void setId(int assignedId) {
        if (id == -1) {
            this.id = assignedId;
        }
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

    public String setStateActive() {
        if (isStateActive) {
            throw new CantActiveAlreadyActivatedEquipmentException();
        }

        isStateActive = true;
        return "";
    }

    public String setStateInactive() {
        if (!isStateActive) {
            throw new CantDeactivateAlreadyDeactivatedEquipmentException();
        }

        isStateActive = false;
        return "";
    }

    public int getId() {
        return id;
    }

}
