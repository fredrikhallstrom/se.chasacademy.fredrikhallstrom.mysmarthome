package se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotypes;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository implements SmartEquipmentRepository {
    private final Map<SmartEquipment, Integer> smartEquipmentRepositoryMap = new HashMap<>();
    private static int uniqueCounterId = 1000;

    public InMemoryRepository() {}

    public int getAndIncrementUniqueCounter() {
        int uniqueCounterIdToReturn = getCurrentUniqueCounter();
        uniqueCounterId++;
        return uniqueCounterIdToReturn;
    }

    public int getCurrentUniqueCounter() {
        return uniqueCounterId;
    }

    @Override
    public Map<SmartEquipment, Integer> getSmartEquipmentRepositoryMap() {
        return Map.copyOf(smartEquipmentRepositoryMap);
    }

    @Override
    public int insertSmartEquipment(SmartEquipment smartEquipment) {
        int assignedId = getAndIncrementUniqueCounter();
        smartEquipmentRepositoryMap.put(smartEquipment, assignedId);
        return assignedId;
    }

    @Override
    public void removeSmartEquipment(SmartEquipment smartEquipment) {
        smartEquipmentRepositoryMap.remove(smartEquipment);
    }

    @Override
    public int getSmartEquipmentId(SmartEquipment wantedSmartEquipmentId) {
        return smartEquipmentRepositoryMap.get(wantedSmartEquipmentId);
    }
}
