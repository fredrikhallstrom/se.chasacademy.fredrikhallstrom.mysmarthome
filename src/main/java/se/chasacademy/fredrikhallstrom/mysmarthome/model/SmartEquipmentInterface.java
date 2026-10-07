package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public interface SmartEquipmentInterface {
    String getName();

    String getStateString();

    LocalDateTime getInstallationTime();

    LocalDateTime getLastActivationTime();

    LocalDateTime getLastDeactivationTime();

    int getId();

    boolean isStateActive();

    void setId(int assignedId);

    String setStateActive();

    String setStateInactive();

    String toString();
}
