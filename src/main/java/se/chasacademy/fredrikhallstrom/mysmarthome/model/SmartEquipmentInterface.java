package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;
import java.util.List;

interface SmartEquipmentInterface {
    String getName();

    List<String> getStateMessages();

    LocalDateTime getInstallationTime();

    LocalDateTime getLastActivationTime();

    LocalDateTime getLastDeactivationTime();

    int getId();

    boolean isStateActive();

    void setId(int assignedId);

    String setStateActive(LocalDateTime activationTime);

    String setStateInactive(LocalDateTime deactivationTime);

    String toString();
}
