package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentstate;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.FoundNoSmartEquipmentByIdException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentNotFoundInRepositoryException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;

import java.util.List;

class OptionSmartEquipmentShowState {
    private final SmartEquipmentHandlerService smartEquipmentHandlerService;
    private final OptionSmartEquipmentStatesHelper optionSmartEquipmentStatesHelper;

    OptionSmartEquipmentShowState(
            SmartEquipmentHandlerService smartEquipmentHandlerService,
            OptionSmartEquipmentStatesHelper optionSmartEquipmentStatesHelper) {

        this.smartEquipmentHandlerService = smartEquipmentHandlerService;
        this.optionSmartEquipmentStatesHelper = optionSmartEquipmentStatesHelper;
    }

    void showSmartEquipmentState() {
        try {
            String messageForQuery = "Which smart equipment do you want to show?";
            SmartEquipment selectedSmartEquipment =
                    optionSmartEquipmentStatesHelper.findSmartEquipmentByIdHelper(messageForQuery);

            List<String> states = smartEquipmentHandlerService.getSmartEquipmentStatesMessages(selectedSmartEquipment);

            IO.println(selectedSmartEquipment.getName() + ": " + states.getFirst());
            states.stream()
                    .skip(1)
                    .map(state -> "\t" + state)
                    .forEach(IO::println);

            IO.println("");
        } catch (SmartEquipmentNotFoundInRepositoryException |
                 MustBeANumberException |
                 FoundNoSmartEquipmentByIdException error) {

            IO.println(error.getMessage());
        }
    }
}
