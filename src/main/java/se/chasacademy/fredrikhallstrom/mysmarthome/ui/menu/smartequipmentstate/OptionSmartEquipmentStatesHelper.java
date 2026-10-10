package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentstate;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchFoundNoSmartEquipmentByIdException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentInRepositoryNotFoundException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;
import se.chasacademy.fredrikhallstrom.mysmarthome.util.InputValidate;

import java.util.Map;

class OptionSmartEquipmentStatesHelper implements InputValidate {
    private final SmartEquipmentHandlerService smartEquipmentHandlerService;

    OptionSmartEquipmentStatesHelper(SmartEquipmentHandlerService smartEquipmentHandlerService) {

        this.smartEquipmentHandlerService = smartEquipmentHandlerService;
    }

    SmartEquipment findSmartEquipmentByIdHelper(String messageForQuery)
            throws SmartEquipmentInRepositoryNotFoundException,
            MustBeANumberException,
            SearchFoundNoSmartEquipmentByIdException {

        Map<Integer, SmartEquipment> availableSmartEquipments =
                smartEquipmentHandlerService.getAvailableSmartEquipmentsSortedMapById();

        if (availableSmartEquipments.isEmpty()) {
            throw new SmartEquipmentInRepositoryNotFoundException();
        }

        IO.print(messageForQuery + "\n\n");
        for (Map.Entry<Integer, SmartEquipment> entry : availableSmartEquipments.entrySet()) {
            IO.print(String.format("Id: %d, Type: %s, Name: %s\n",
                    entry.getKey(),
                    entry.getValue().getClass().getSimpleName(),
                    entry.getValue().getName()
            ));
        }

        int selectedSmartEquipmentId = validateInputStringToInt(IO.readln("\nChoice: "));
        IO.println("");

        return smartEquipmentHandlerService.getSmartEquipmentById(selectedSmartEquipmentId);
    }
}
