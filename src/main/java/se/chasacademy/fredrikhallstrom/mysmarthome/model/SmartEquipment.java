package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentIdCantBeNegativeException;
import se.chasacademy.fredrikhallstrom.mysmarthome.util.TimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public abstract class SmartEquipment implements SmartEquipmentInterface, TimeFormat {
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
        this.lastDeactivationTime = installationTime;
    }

    public String getName() {
        return name;
    }

    public abstract List<String> getStateMessages();

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

    public void setId(int assignedId) throws
            SmartEquipmentIdCantBeNegativeException {

        if (assignedId < 0) {
            throw new SmartEquipmentIdCantBeNegativeException(assignedId);
        } else if (id == -1) {
            this.id = assignedId;
        }
    }

    public String setStateActive(LocalDateTime activationTime)
            throws CantActiveAlreadyActivatedStateException {

        if (isStateActive) {
            throw new CantActiveAlreadyActivatedStateException();
        }

        isStateActive = true;
        this.lastActivationTime = activationTime;

        return "";
    }

    public String setStateInactive(LocalDateTime deactivationTime)
            throws CantDeactivateAlreadyDeactivatedStateException {

        if (!isStateActive) {
            throw new CantDeactivateAlreadyDeactivatedStateException();
        }

        isStateActive = false;
        this.lastDeactivationTime = deactivationTime;

        return "";
    }

    public String toString() {
        List<String> states = getStateMessages();
        LocalDateTime lastStateChangeTime;

        if (getLastActivationTime().isAfter(getLastDeactivationTime())) {
            lastStateChangeTime = getLastActivationTime();
        } else {
            lastStateChangeTime = getLastDeactivationTime();
        }

        String mainState = String.format("Type: %s, ID: %d, %s(Installed: %s) - Current state: %s @ %s",
                getClass().getSimpleName(),
                getId(),
                getName(),
                formatLocalDateTimeAndTimeZoneForSystemDefault(getInstallationTime()),
                states.getFirst(),
                formatLocalDateTimeAndTimeZoneForSystemDefault(lastStateChangeTime)
        );

        if (states.size() > 1) {
            String otherStates = String.join(", ", states.subList(1, states.size()));
            return mainState + "\n\tOther state/-s: " + otherStates;
        }

        return mainState;
    }

}
