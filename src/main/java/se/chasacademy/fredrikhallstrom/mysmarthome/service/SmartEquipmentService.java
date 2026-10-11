package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.*;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipmentType;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepositoryInterface;
import se.chasacademy.fredrikhallstrom.mysmarthome.util.InputValidate;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SmartEquipmentService implements SmartEquipmentServiceInterface, InputValidate {
    private final SmartEquipmentRepositoryInterface smartEquipmentRepository;
    private final Clock clock;

    public SmartEquipmentService(SmartEquipmentRepositoryInterface smartEquipmentRepository, Clock clock) {
        this.smartEquipmentRepository = smartEquipmentRepository;
        this.clock = clock;
    }

    @Override
    public SmartEquipment addSmartEquipment(SmartEquipment smartEquipment)
            throws CreationOfSmartEquipmentDidFailSomewhereException, SmartEquipmentIdCantBeNegativeException {

        int assignedId = smartEquipmentRepository.insertSmartEquipment(smartEquipment);
        smartEquipment.setId(assignedId);
        if (smartEquipment.getId() == assignedId) {
            return smartEquipment;
        }
        throw new CreationOfSmartEquipmentDidFailSomewhereException();
    }

    @Override
    public boolean removeSmartEquipment(String smartEquipmentIdStringToRemove)
            throws MustBeANumberException,
            RemoveOfSmartEquipmentDidFailSomewhereException,
            FoundNoSmartEquipmentByIdException,
            SmartEquipmentIdCantBeNegativeException {

        int smartEquipmentIdToRemove = validateInputStringToInt(smartEquipmentIdStringToRemove);

        if (smartEquipmentIdToRemove < 0) {
            throw new SmartEquipmentIdCantBeNegativeException(smartEquipmentIdToRemove);
        }

        if (smartEquipmentRepository.getSmartEquipmentRepositoryMap()
                .keySet()
                .stream()
                .noneMatch(smartEquipment -> smartEquipment.getId() == smartEquipmentIdToRemove)) {
            throw new FoundNoSmartEquipmentByIdException(smartEquipmentIdToRemove);
        }

        return smartEquipmentRepository.removeSmartEquipment(smartEquipmentIdToRemove);

    }

    @Override
    public SmartEquipment createSmartEquipment(
            String name,
            SmartEquipmentType type)
            throws SmartEquipmentIdCantBeNegativeException,
            InvalidSmartEquipmentNameException,
            CreationOfSmartEquipmentDidFailSomewhereException {

        validateSmartEquipmentName(name, type);

        SmartEquipment smartEquipment = type.getCreator()
                .create(name, getCurrentLocalDateTime());
        return addSmartEquipment(smartEquipment);
    }

    @Override
    public List<SmartEquipment> getAllSmartEquipmentAsList()
            throws SmartEquipmentNotFoundInRepositoryException {

        List<SmartEquipment> listOfSmartEquipment = new ArrayList<>(
                smartEquipmentRepository.getSmartEquipmentRepositoryMap()
                        .keySet());

        if (listOfSmartEquipment.isEmpty()) {
            throw new SmartEquipmentNotFoundInRepositoryException();
        }

        return listOfSmartEquipment;
    }

    @Override
    public LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now(clock);
    }

    @Override
    public void validateSmartEquipmentName(
            String smartEquipmentName,
            SmartEquipmentType typeChoice)
            throws InvalidSmartEquipmentNameException {

        boolean evaluatedExpressionOfNameEqualsType;
        try {
            evaluatedExpressionOfNameEqualsType = this.getAllSmartEquipmentAsList().stream().anyMatch(
                    smartEquipment -> smartEquipment.getName()
                            .equalsIgnoreCase(smartEquipmentName));
        } catch (SmartEquipmentNotFoundInRepositoryException e) {
            evaluatedExpressionOfNameEqualsType = false;
        }

        if (smartEquipmentName == null
                || !smartEquipmentName.matches("[a-zA-Z][a-zA-Z0-9 ]*")
                || evaluatedExpressionOfNameEqualsType
                || smartEquipmentName.equalsIgnoreCase(typeChoice.getDisplayName())
        ) {
            throw new InvalidSmartEquipmentNameException();
        }
    }

    @Override
    public SortedMap<Integer, SmartEquipment> getAllSmartEquipmentsSortedMapById()
            throws SmartEquipmentNotFoundInRepositoryException {

        Map<SmartEquipment, Integer> repoMap = smartEquipmentRepository.getSmartEquipmentRepositoryMap();
        return new TreeMap<>(repoMap
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getValue, Map.Entry::getKey))
        );
    }

    @Override
    public SmartEquipment getSmartEquipmentById(int smartEquipmentId)
            throws FoundNoSmartEquipmentByIdException {
        SmartEquipment smartEquipment = this.getAllSmartEquipmentsSortedMapById().get(smartEquipmentId);
        if (smartEquipment == null) {
            throw new FoundNoSmartEquipmentByIdException(smartEquipmentId);
        }
        return smartEquipment;
    }

}