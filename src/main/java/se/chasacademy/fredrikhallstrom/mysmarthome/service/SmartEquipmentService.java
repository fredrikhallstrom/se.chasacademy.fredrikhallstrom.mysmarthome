package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.*;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipmentType;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepositoryInterface;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SmartEquipmentService {
    private final SmartEquipmentRepositoryInterface smartEquipmentRepository;
    private final Clock clock;

    public SmartEquipmentService(SmartEquipmentRepositoryInterface smartEquipmentRepository, Clock clock) {
        this.smartEquipmentRepository = smartEquipmentRepository;
        this.clock = clock;
    }

    public SmartEquipment addSmartEquipment(SmartEquipment smartEquipment)
            throws CreationOfSmartEquipmentDidFailSomewhereException, SmartEquipmentIdCantBeNegativeException {

        int assignedId = smartEquipmentRepository.insertSmartEquipment(smartEquipment);
        smartEquipment.setId(assignedId);
        if (smartEquipment.getId() == assignedId) {
            return smartEquipment;
        }
        throw new CreationOfSmartEquipmentDidFailSomewhereException();
    }

    public boolean removeSmartEquipment(String smartEquipmentIdStringToRemove)
            throws MustBeANumberException, RemoveOfSmartEquipmentDidFailSomewhereException {

        int smartEquipmentIdToRemove = validateSmartEquipmentId(smartEquipmentIdStringToRemove);
        return smartEquipmentRepository.removeSmartEquipment(smartEquipmentIdToRemove);

    }

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

    public List<SmartEquipment> AllSmartEquipmentAsList()
            throws SmartEquipmentInRepositoryNotFoundException {

        List<SmartEquipment> listOfSmartEquipment = new ArrayList<>(smartEquipmentRepository.getSmartEquipmentRepositoryMap().keySet());

        if (listOfSmartEquipment.isEmpty()) {
            throw new SmartEquipmentInRepositoryNotFoundException();
        }

        return listOfSmartEquipment;
    }

    private LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now(clock);
    }

    public void validateSmartEquipmentName(
            String smartEquipmentName,
            SmartEquipmentType typeChoice)
            throws InvalidSmartEquipmentNameException {

        boolean evaluatedExpressionOfNameEqualsType;
        try {
            evaluatedExpressionOfNameEqualsType = AllSmartEquipmentAsList().stream().anyMatch(
                    smartEquipment -> smartEquipment.getName()
                            .equalsIgnoreCase(smartEquipmentName));
        } catch (SmartEquipmentInRepositoryNotFoundException e) {
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

    public int validateSmartEquipmentId(String smartEquipmentIdString)
            throws MustBeANumberException {

        try {
            return Integer.parseInt(smartEquipmentIdString);
        } catch (NumberFormatException e) {
            throw new MustBeANumberException();
        }
    }
}