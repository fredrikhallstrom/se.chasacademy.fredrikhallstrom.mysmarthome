package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.*;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SmartEquipmentService {
    private final SmartEquipmentRepository smartEquipmentRepository;
    private final Clock clock;

    public SmartEquipmentService(SmartEquipmentRepository smartEquipmentRepository, Clock clock) {
        this.smartEquipmentRepository = smartEquipmentRepository;
        this.clock = clock;
    }

    public SmartEquipment addSmartEquipment(SmartEquipment smartEquipment) throws CreationOfSmartEquipmentDidFailSomewhereException {
        int assignedId = smartEquipmentRepository.insertSmartEquipment(smartEquipment);
        smartEquipment.setId(assignedId);
        if  (smartEquipment.getId() == assignedId) {
            return smartEquipment;
        }
        throw new CreationOfSmartEquipmentDidFailSomewhereException();
    }

    public boolean removeSmartEquipment(String smartEquipmentIdStringToRemove) throws MustBeANumberException, RemoveOfSmartEquipmentDidFailSomewhereException {
        int smartEquipmentIdToRemove;
        try {
            smartEquipmentIdToRemove = Integer.parseInt(String.valueOf(smartEquipmentIdStringToRemove));
        } catch (NumberFormatException e) {
            throw new MustBeANumberException();
        }

        return smartEquipmentRepository.removeSmartEquipment(smartEquipmentIdToRemove);


    }

    public List<SmartEquipment> AllSmartEquipmentAsList() throws SmartEquipmentInRepositoryNotFoundException {
        List<SmartEquipment> listOfSmartEquipment = new ArrayList<>(smartEquipmentRepository.getSmartEquipmentRepositoryMap().keySet());

        if (listOfSmartEquipment.isEmpty()) {
            throw new SmartEquipmentInRepositoryNotFoundException();
        }

        return listOfSmartEquipment;
    }

    public LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now(clock);
    }
}