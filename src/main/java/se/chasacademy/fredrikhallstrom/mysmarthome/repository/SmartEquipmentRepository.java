package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

import java.util.HashMap;
import java.util.Map;

public class SmartEquipmentRepository {
    private final Map<SmartEquipment, Integer> smartEquipmentRepositoryMap = new HashMap<>();


    public SmartEquipmentRepository() {}

    public Map<SmartEquipment, Integer> getSmartEquipmentRepositoryMap() {
        return smartEquipmentRepositoryMap;
    }
}
