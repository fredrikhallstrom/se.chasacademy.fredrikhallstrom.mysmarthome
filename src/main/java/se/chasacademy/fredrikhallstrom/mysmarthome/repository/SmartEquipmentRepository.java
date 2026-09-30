package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

import java.util.HashMap;
import java.util.Map;

public class SmartEquipmentRepository {
    private final Map<SmartEquipment, Integer> smartEquipmentRepositoryMap = new HashMap<>();


    public SmartEquipmentRepository() {}

    public Map<SmartEquipment, Integer> getSmartEquipmentRepositoryMap() {
        return Map.copyOf(smartEquipmentRepositoryMap);
    }

    public void insertSmartEquipment(SmartEquipment smartEquipment, Integer assignedId) {
        smartEquipmentRepositoryMap.put(smartEquipment, assignedId);
    }

    public void removeSmartEquipment(SmartEquipment smartEquipment) {
        smartEquipmentRepositoryMap.remove(smartEquipment);
    }

    public void getSmartEquipment(SmartEquipment smartEquipment) {
        smartEquipmentRepositoryMap.get(smartEquipment);
    }

}
