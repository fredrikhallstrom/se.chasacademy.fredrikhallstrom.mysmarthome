package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.InsertOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentNotFoundException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

import java.util.Map;

public interface SmartEquipmentRepositoryInterface {

    Map<SmartEquipment, Integer> getSmartEquipmentRepositoryMap() throws SmartEquipmentNotFoundException;

    int insertSmartEquipment(SmartEquipment smartEquipment) throws InsertOfSmartEquipmentDidFailSomewhereException;

    boolean removeSmartEquipment(int smartEquipmentIdToRemove) throws RemoveOfSmartEquipmentDidFailSomewhereException;

}
