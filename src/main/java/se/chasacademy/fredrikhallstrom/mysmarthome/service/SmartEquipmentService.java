package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CreationOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

import java.time.Clock;

public class SmartEquipmentService {
    private final SmartEquipmentRepository smartEquipmentRepository;

    public SmartEquipmentService(SmartEquipmentRepository smartEquipmentRepository, Clock clock) {
        this.smartEquipmentRepository = smartEquipmentRepository;
    }

    public int addSmartEquipment(SmartEquipment smartEquipment) {
        int assignedId = smartEquipmentRepository.insertSmartEquipment(smartEquipment);
        smartEquipment.setId(assignedId);
        if  (smartEquipment.getId() == assignedId) {
            return smartEquipment.getId();
        }
        throw new CreationOfSmartEquipmentDidFailSomewhereException();
    }

    public void removeSmartEquipment(SmartEquipment smartEquipmentToRemove) {
        if (!smartEquipmentRepository.getSmartEquipmentRepositoryMap().containsKey(smartEquipmentToRemove)) {
            throw new RemoveOfSmartEquipmentDidFailSomewhereException(smartEquipmentToRemove);
        }

        smartEquipmentRepository.removeSmartEquipment(smartEquipmentToRemove);
    }

}