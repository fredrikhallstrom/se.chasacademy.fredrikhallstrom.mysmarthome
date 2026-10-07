package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

import java.util.Map;

sealed interface SmartEquipmentRepositoryInterface permits SmartEquipmentRepository {

    Map<SmartEquipment, Integer> getSmartEquipmentRepositoryMap();

    int insertSmartEquipment(SmartEquipment smartEquipment);

    void removeSmartEquipment(SmartEquipment smartEquipment);

    int getSmartEquipmentId(SmartEquipment smartEquipment);

    int getAndIncrementUniqueCounter();

    int getCurrentUniqueCounter();

}
