package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.InsertOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

import java.util.HashMap;
import java.util.Map;

public abstract class SmartEquipmentRepository implements SmartEquipmentRepositoryInterface {
    protected final Map<SmartEquipment, Integer> smartEquipmentRepositoryMap = new HashMap<>();

    public Map<SmartEquipment, Integer> getSmartEquipmentRepositoryMap() {
        return Map.copyOf(smartEquipmentRepositoryMap);
    }

    public abstract boolean removeSmartEquipment(int smartEquipmentIdToRemove) throws RemoveOfSmartEquipmentDidFailSomewhereException;

    public abstract int insertSmartEquipment(SmartEquipment smartEquipment) throws InsertOfSmartEquipmentDidFailSomewhereException;
}
