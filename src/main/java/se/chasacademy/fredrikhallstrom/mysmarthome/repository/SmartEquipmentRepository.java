package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

import java.util.HashMap;
import java.util.Map;

public interface SmartEquipmentRepository {
    Map<SmartEquipment, Integer> smartEquipmentRepositoryMap = new HashMap<>();

    Map<SmartEquipment, Integer> getSmartEquipmentRepositoryMap();

    public void insertSmartEquipment(SmartEquipment smartEquipment);

    public void removeSmartEquipment(SmartEquipment smartEquipment);

    public void getSmartEquipment(SmartEquipment smartEquipment);

    public int getAndIncrementUniqueCounter();

    public int getCurrentUniqueCounter();

}
