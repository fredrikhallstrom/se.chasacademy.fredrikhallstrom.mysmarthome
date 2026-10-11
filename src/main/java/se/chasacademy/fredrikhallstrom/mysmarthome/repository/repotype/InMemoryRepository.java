package se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.InsertOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

public class InMemoryRepository extends SmartEquipmentRepository {
    private int uniqueCounterId = 1000;

    public InMemoryRepository() {
        super();
    }

    private int getAndIncrementUniqueCounter() {
        int uniqueCounterIdToReturn = uniqueCounterId;
        uniqueCounterId++;
        return uniqueCounterIdToReturn;
    }

    @Override
    public int insertSmartEquipment(SmartEquipment smartEquipment) throws InsertOfSmartEquipmentDidFailSomewhereException {
        if (smartEquipment == null) {
            throw new InsertOfSmartEquipmentDidFailSomewhereException();
        }

        int assignedId = getAndIncrementUniqueCounter();

        smartEquipmentRepositoryMap.put(smartEquipment, assignedId);
        return assignedId;
    }

    @Override
    public boolean removeSmartEquipment(int smartEquipmentIdToRemove)
            throws RemoveOfSmartEquipmentDidFailSomewhereException {

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
