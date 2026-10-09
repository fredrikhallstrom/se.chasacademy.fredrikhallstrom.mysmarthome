package se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

public class InMemoryRepository extends SmartEquipmentRepository {
    private int uniqueCounterId = 1000;

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
    public boolean removeSmartEquipment(int smartEquipmentIdToRemove) {
        if (smartEquipmentRepositoryMap.keySet().stream().noneMatch(
                smartEquipment -> smartEquipment.getId() == smartEquipmentIdToRemove)
                ||
                smartEquipmentRepositoryMap.isEmpty()) {
            throw new RemoveOfSmartEquipmentDidFailSomewhereException();
        }

        smartEquipmentRepositoryMap.keySet().removeIf(
            smartEquipment -> smartEquipment.getId() == smartEquipmentIdToRemove
        );

        return true;
    }

}
