package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

import java.util.Map;

public interface SmartEquipmentRepositoryInterface {

    Map<SmartEquipment, Integer> getSmartEquipmentRepositoryMap();

    int insertSmartEquipment(SmartEquipment smartEquipment);

    boolean removeSmartEquipment(int smartEquipmentIdToRemove);

}
