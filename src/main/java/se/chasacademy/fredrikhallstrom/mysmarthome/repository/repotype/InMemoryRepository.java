package se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

public class InMemoryRepository extends SmartEquipmentRepository {
    private static int uniqueCounterId = 1000;

    public InMemoryRepository() {
        super();
    }

    public int getAndIncrementUniqueCounter() {
        int uniqueCounterIdToReturn = getCurrentUniqueCounter();
        uniqueCounterId++;
        return uniqueCounterIdToReturn;
    }

    public int getCurrentUniqueCounter() {
        return uniqueCounterId;
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
