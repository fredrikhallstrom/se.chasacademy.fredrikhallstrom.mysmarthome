package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CreationOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchFoundNoSmartEquipmentsException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SmartEquipmentService {
    private final SmartEquipmentRepository smartEquipmentRepository;

    public SmartEquipmentService(SmartEquipmentRepository smartEquipmentRepository, Clock clock) {
        this.smartEquipmentRepository = smartEquipmentRepository;
    }

    public void addSmartEquipment(SmartEquipment smartEquipment) {
        int assignedId = smartEquipmentRepository.insertSmartEquipment(smartEquipment);
        smartEquipment.setId(assignedId);
        if  (smartEquipment.getId() == assignedId) {
            return;
        }
        throw new CreationOfSmartEquipmentDidFailSomewhereException();
    }

    public void removeSmartEquipment(SmartEquipment smartEquipmentToRemove) {
        if (!smartEquipmentRepository.getSmartEquipmentRepositoryMap().containsKey(smartEquipmentToRemove)) {
            throw new RemoveOfSmartEquipmentDidFailSomewhereException(smartEquipmentToRemove);
        }

        smartEquipmentRepository.removeSmartEquipment(smartEquipmentToRemove);
    }

    public List<SmartEquipment> searchSmartEquipmentByName(String searchTerm, SearchSortingTypes sortingType) {
        List<SmartEquipment> smartEquipments = new ArrayList<>(List.copyOf(smartEquipmentRepository.getSmartEquipmentRepositoryMap().keySet()));
        List<SmartEquipment> foundSmartEquipment = new ArrayList<>();
        for (SmartEquipment smartEquipment : smartEquipments) {
            if (smartEquipment.getName().contains(searchTerm)) {
                foundSmartEquipment.add(smartEquipment);
            }
        }

        if (foundSmartEquipment.isEmpty())
            throw new SearchFoundNoSmartEquipmentsException(searchTerm);

        if (sortingType == SearchSortingTypes.ASCENDING) {
            return foundSmartEquipment.stream()
                    .sorted(Comparator.comparing(SmartEquipment::getName))
                    .toList();
        } else if (sortingType == SearchSortingTypes.DESCENDING) {
            return foundSmartEquipment.stream()
                    .sorted(Comparator.comparing(SmartEquipment::getName))
                    .toList()
                    .reversed();
        }

        return foundSmartEquipment;

    }
}