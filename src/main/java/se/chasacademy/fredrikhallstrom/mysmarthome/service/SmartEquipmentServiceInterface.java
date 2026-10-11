package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CreationOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.InvalidSmartEquipmentNameException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.FoundNoSmartEquipmentByIdException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentIdCantBeNegativeException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentNotFoundInRepositoryException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipmentType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedMap;

public interface SmartEquipmentServiceInterface {

    SmartEquipment addSmartEquipment(SmartEquipment smartEquipment)
            throws CreationOfSmartEquipmentDidFailSomewhereException, SmartEquipmentIdCantBeNegativeException;

    boolean removeSmartEquipment(String smartEquipmentIdStringToRemove)
            throws MustBeANumberException,
            RemoveOfSmartEquipmentDidFailSomewhereException,
            FoundNoSmartEquipmentByIdException,
            SmartEquipmentIdCantBeNegativeException;

    SmartEquipment createSmartEquipment(
            String name,
            SmartEquipmentType type)
            throws SmartEquipmentIdCantBeNegativeException,
            InvalidSmartEquipmentNameException,
            CreationOfSmartEquipmentDidFailSomewhereException;

    List<SmartEquipment> getAllSmartEquipmentAsList()
            throws SmartEquipmentNotFoundInRepositoryException;

    LocalDateTime getCurrentLocalDateTime();

    SortedMap<Integer, SmartEquipment> getAllSmartEquipmentsSortedMapById()
            throws SmartEquipmentNotFoundInRepositoryException;

    SmartEquipment getSmartEquipmentById(int smartEquipmentId)
            throws FoundNoSmartEquipmentByIdException;

}
