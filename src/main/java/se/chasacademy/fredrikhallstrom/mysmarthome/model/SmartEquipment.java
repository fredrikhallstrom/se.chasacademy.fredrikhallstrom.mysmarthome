package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class SmartEquipment implements SmartEquipmentInterface {
    protected int id = -1;
    protected String name;
    protected LocalDateTime installationTime;
    protected LocalDateTime lastActivationTime;
    protected LocalDateTime lastDeactivationTime;
    protected boolean isStateActive = false;
    private final Clock clock;

    SmartEquipment(String name,Clock clock) {
        this.name = name;
        this.installationTime = LocalDateTime.now(clock);
        this.lastActivationTime = LocalDateTime.now(clock);
        this.lastDeactivationTime  = LocalDateTime.now(clock);
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
            return String.format("%s(Installed: %s) - Current state: %s @ %s , Other state/-s: %s",
                    getName(),
                    getInstallationTime().format(SWEDISH_DATE_TIME_FORMAT),
                    splitStatusMessageStringAsList.getFirst(),
                    lastStateChangeTime.format(SWEDISH_DATE_TIME_FORMAT),
                    nonMainState

            );

        } else {
            return String.format("%s(Installed: %s) - Current state: %s @ %s",
                    getName(),
                    getInstallationTime().format(SWEDISH_DATE_TIME_FORMAT),
                    statusMessageString,
                    lastStateChangeTime.format(SWEDISH_DATE_TIME_FORMAT)
            );
        }
    }

}
