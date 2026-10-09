package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

import java.util.Map;

sealed interface SmartEquipmentRepositoryInterface permits SmartEquipmentRepository {

    Map<SmartEquipment, Integer> getSmartEquipmentRepositoryMap();

    int insertSmartEquipment(SmartEquipment smartEquipment);

    boolean removeSmartEquipment(int smartEquipmentIdToRemove);

    int getAndIncrementUniqueCounter();

    int getCurrentUniqueCounter();

}
