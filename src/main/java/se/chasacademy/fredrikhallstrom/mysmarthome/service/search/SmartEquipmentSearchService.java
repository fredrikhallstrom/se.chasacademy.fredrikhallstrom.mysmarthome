package se.chasacademy.fredrikhallstrom.mysmarthome.service.search;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.FoundNoSmartEquipmentByIdException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.FoundNoSmartEquipmentsByNameException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchSortingTypeCantBeNullException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentNotFoundInRepositoryException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentServiceInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SmartEquipmentSearchService {
    private final SmartEquipmentServiceInterface smartEquipmentService;

    public SmartEquipmentSearchService(SmartEquipmentServiceInterface smartEquipmentService) {
        this.smartEquipmentService = smartEquipmentService;
    }

    public List<SmartEquipment> searchSmartEquipmentByName(
            String searchTerm,
            SearchSortingType sortingType)
            throws
            FoundNoSmartEquipmentsByNameException,
            SmartEquipmentNotFoundInRepositoryException,
            SearchSortingTypeCantBeNullException {

        if (sortingType == null) {
            throw new SearchSortingTypeCantBeNullException();
        }

        List<SmartEquipment> smartEquipments = smartEquipmentService.getAllSmartEquipmentAsList();
        List<SmartEquipment> foundSmartEquipment = new ArrayList<>();

        for (SmartEquipment smartEquipment : smartEquipments) {
            if (smartEquipment.getName().contains(searchTerm)) {
                foundSmartEquipment.add(smartEquipment);
            }
        }

        if (foundSmartEquipment.isEmpty())
            throw new FoundNoSmartEquipmentsByNameException(searchTerm);

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

    public SmartEquipment searchSmartEquipmentById(int id)
            throws FoundNoSmartEquipmentByIdException,
            SmartEquipmentNotFoundInRepositoryException {

        for (SmartEquipment smartEquipment : smartEquipmentService.getAllSmartEquipmentAsList()) {
            if (smartEquipment.getId() == id) {
                return smartEquipment;
            }
        }

        throw new FoundNoSmartEquipmentByIdException(id);
    }

    public List<SearchSortingType> getSortTypes() {
        return Arrays.stream(SearchSortingType.values()).toList();
    }

    public String getSmartEquipmentToString(SmartEquipment smartEquipment)
    throws SmartEquipmentNotFoundInRepositoryException {
        if (smartEquipment == null) {
            throw new SmartEquipmentNotFoundInRepositoryException();
        }

        return smartEquipment.toString();
    }
}
