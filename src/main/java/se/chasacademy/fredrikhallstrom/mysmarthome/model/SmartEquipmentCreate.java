package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public interface SmartEquipmentCreate {
    SmartEquipment create(String name, LocalDateTime installationTime);
}
