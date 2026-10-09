package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class SmartEquipment implements SmartEquipmentInterface {
    protected int id = -1;
    protected String name;
    protected LocalDateTime installationTime;
    protected LocalDateTime lastActivationTime;
    protected LocalDateTime lastDeactivationTime;
    protected boolean isStateActive = false;

    SmartEquipment(String name, LocalDateTime installationTime) {
        this.name = name;
        this.installationTime = installationTime;
        this.lastActivationTime = installationTime;
        this.lastDeactivationTime  = installationTime;
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

    public String setStateActive(LocalDateTime activationTime) {
        if (isStateActive) {
            throw new CantActiveAlreadyActivatedEquipmentException();
        }

        isStateActive = true;
        this.lastActivationTime = activationTime;

        return "";
    }

    public String setStateInactive(LocalDateTime deactivationTime) {
        if (!isStateActive) {
            throw new CantDeactivateAlreadyDeactivatedEquipmentException();
        }

        isStateActive = false;
        this.lastDeactivationTime = deactivationTime;

        return "";
    }

    public String toString() {
        String statusMessageString = getStateString();
        List<String> splitStatusMessageStringAsList = List.of(statusMessageString.split(";"));
        LocalDateTime lastStateChangeTime;
        DateTimeFormatter SWEDISH_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (getLastActivationTime().isAfter(getLastDeactivationTime())) {
            lastStateChangeTime = getLastActivationTime();
        } else {
            lastStateChangeTime = getLastDeactivationTime();
        }

        if (splitStatusMessageStringAsList.size() > 1) {
            StringBuilder nonMainState = new StringBuilder();
            for (int i = 0; i < splitStatusMessageStringAsList.size(); i++) {
                if (i == 0) {
                    continue;
                }
                if (i != 1) {
                    nonMainState.append(", ");
                }

                nonMainState.append(splitStatusMessageStringAsList.get(i));
            }
            return String.format("Type: %s, ID: %d, %s(Installed: %s) - Current state: %s @ %s , Other state/-s: %s",
                    getClass().getSimpleName(),
                    getId(),
                    getName(),
                    getInstallationTime().atZone(ZoneId.systemDefault()).format(SWEDISH_DATE_TIME_FORMAT),
                    splitStatusMessageStringAsList.getFirst().split(", ",2)[1],
                    lastStateChangeTime.atZone(ZoneId.systemDefault()).format(SWEDISH_DATE_TIME_FORMAT),
                    nonMainState

            );

        } else {
            return String.format("Type: %s, ID: %d, %s(Installed: %s) - Current state: %s @ %s",
                    getClass().getSimpleName(),
                    getId(),
                    getName(),
                    getInstallationTime().format(SWEDISH_DATE_TIME_FORMAT),
                    statusMessageString.split(", ",2)[1],
                    lastStateChangeTime.format(SWEDISH_DATE_TIME_FORMAT)
            );
        }
    }

}
