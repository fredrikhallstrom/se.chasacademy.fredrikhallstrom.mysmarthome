package se.chasacademy.fredrikhallstrom.mysmarthome.service.search;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchFoundNoSmartEquipmentByIdException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchFoundNoSmartEquipmentsByNameException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SmartEquipmentSearchService {
    private final SmartEquipmentService smartEquipmentService;

    public SmartEquipmentSearchService(SmartEquipmentService smartEquipmentService) {
        this.smartEquipmentService = smartEquipmentService;
    }

    public List<SmartEquipment> searchSmartEquipmentByName(String searchTerm, SearchSortingType sortingType, SmartEquipmentService smartEquipmentService)
            throws SearchFoundNoSmartEquipmentsByNameException {

        List<SmartEquipment> smartEquipments = smartEquipmentService.AllSmartEquipmentAsList();
        List<SmartEquipment> foundSmartEquipment = new ArrayList<>();

        for (SmartEquipment smartEquipment : smartEquipments) {
            if (smartEquipment.getName().contains(searchTerm)) {
                foundSmartEquipment.add(smartEquipment);
            }
        }

        if (foundSmartEquipment.isEmpty())
            throw new SearchFoundNoSmartEquipmentsByNameException(searchTerm);

        if (sortingType == SearchSortingType.ASCENDING) {
            return foundSmartEquipment.stream()
                    .sorted(Comparator.comparing(SmartEquipment::getName))
                    .toList();
        } else if (sortingType == SearchSortingType.DESCENDING) {
            return foundSmartEquipment.stream()
                    .sorted(Comparator.comparing(SmartEquipment::getName))
                    .toList()
                    .reversed();
        }

        return foundSmartEquipment;

    }

    public SmartEquipment searchSmartEquipmentById(int id) throws SearchFoundNoSmartEquipmentByIdException {
        for (SmartEquipment smartEquipment : smartEquipmentService.AllSmartEquipmentAsList()) {
            if (smartEquipment.getId() == id) {
                return smartEquipment;
            }
        }

        throw new SearchFoundNoSmartEquipmentByIdException(id);
    }
}
