package se.chasacademy.fredrikhallstrom.mysmarthome.service.handler;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.*;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.ExtraStateControllable;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SmartEquipmentHandlerService {
    private final Clock clock;
    private final SmartEquipmentService smartEquipmentService;

    public SmartEquipmentHandlerService(SmartEquipmentService smartEquipmentService, Clock clock) {
        this.clock = clock;
        this.smartEquipmentService = smartEquipmentService;
    }

    public String setSmartEquipmentState(SmartEquipment smartEquipment, boolean turnStateOn, boolean extraState)
            throws CantActiveAlreadyActivatedStateException,
            CantDeactivateAlreadyDeactivatedStateException,
            SmartEquipmentHasNoExtraStateException {

        if (!extraState) {
            if (turnStateOn) {
                return smartEquipment.setStateActive(LocalDateTime.now(clock));
            }
            return smartEquipment.setStateInactive(LocalDateTime.now(clock));

        } else if (smartEquipment instanceof ExtraStateControllable extraStateControllable) {
            if (turnStateOn) {
                return extraStateControllable.setExtraStateOn();
            }
            return extraStateControllable.setExtraStateOff();
        }

        throw new SmartEquipmentHasNoExtraStateException();
    }

    public List<String> getSmartEquipmentStatesMessages(SmartEquipment smartEquipment) {
        return smartEquipment.getStateMessages();

    }

    public SmartEquipment getSmartEquipmentById(int smartEquipmentId)
            throws SearchFoundNoSmartEquipmentByIdException {

        return smartEquipmentService.getSmartEquipmentById(smartEquipmentId);
    }

    public Map<Integer, SmartEquipment> getAvailableSmartEquipmentsSortedMapById()
            throws SmartEquipmentNotFoundException {

        return smartEquipmentService.getAvailableSmartEquipmentsSortedMapById();
    }

    public String getSmartEquipmentExtraStateString(SmartEquipment smartEquipment)
            throws SmartEquipmentHasNoExtraStateException {

        if (smartEquipment instanceof ExtraStateControllable extraStateControllable) {
            return extraStateControllable.getExtraStateString();
        }
        throw new SmartEquipmentHasNoExtraStateException();
    }
}
