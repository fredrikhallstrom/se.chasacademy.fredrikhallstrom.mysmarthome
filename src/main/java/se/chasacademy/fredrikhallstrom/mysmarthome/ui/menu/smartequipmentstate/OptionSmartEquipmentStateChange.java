package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentstate;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchFoundNoSmartEquipmentByIdException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentHasNoExtraStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentInRepositoryNotFoundException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.ExtraStateControllable;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;
import se.chasacademy.fredrikhallstrom.mysmarthome.util.InputValidate;

class OptionSmartEquipmentStateChange implements InputValidate {
    private final SmartEquipmentHandlerService smartEquipmentHandlerService;
    private final OptionSmartEquipmentStatesHelper optionSmartEquipmentStatesHelper;

    OptionSmartEquipmentStateChange(
            SmartEquipmentHandlerService smartEquipmentHandlerService,
            OptionSmartEquipmentStatesHelper optionSmartEquipmentStatesHelper) {

        this.optionSmartEquipmentStatesHelper = optionSmartEquipmentStatesHelper;
        this.smartEquipmentHandlerService = smartEquipmentHandlerService;
    }

    void changeSmartEquipmentState() {
        try {
            String messageForQuery = "Which smart equipment do you want to change state on?";
            SmartEquipment selectedSmartEquipment =
                    optionSmartEquipmentStatesHelper.findSmartEquipmentByIdHelper(messageForQuery);
            int choice = Integer.MIN_VALUE;
            if (selectedSmartEquipment instanceof ExtraStateControllable) {
                IO.println("""
                        1. Do you want to turn on or off the main state?
                        2. Do you want to turn on or off the extra state?
                        """);
                choice = validateInputStringToInt(IO.readln("Enter your choice: "));
            }
            if (choice == 1 || choice == Integer.MIN_VALUE) {
                String stateMessage = optionSetStateHelper(selectedSmartEquipment, false);
                IO.println(stateMessage);

            } else if (choice == 2) {
                String extraStateMessage = optionSetStateHelper(selectedSmartEquipment, true);
                IO.println(extraStateMessage);
            } else {
                IO.println("Not a valid choice!!");
            }


        } catch (SmartEquipmentInRepositoryNotFoundException |
                 MustBeANumberException |
                 SearchFoundNoSmartEquipmentByIdException |
                 CantActiveAlreadyActivatedStateException |
                 CantDeactivateAlreadyDeactivatedStateException |
                 SmartEquipmentHasNoExtraStateException error) {

            IO.println(error.getMessage());
        }
    }

    private String optionSetStateHelper(SmartEquipment smartEquipment, boolean isExtraState) {
        String stateType;
        String state;

        if (isExtraState) {
            stateType = "extra";
            state = smartEquipmentHandlerService.getSmartEquipmentExtraStateString(smartEquipment);
        } else {
            stateType = "main";
            state = smartEquipmentHandlerService.getSmartEquipmentStatesMessages(smartEquipment).getFirst();
        }

        IO.println(String.format("Do you want to turn on or off the %s state?\n", stateType));
        IO.println(String.format("The %s state is currently: %s\n", stateType, state));

        IO.println("""
                1. Turn on
                2. Turn off
                3. Abort
                """);

        int stateChoice = validateInputStringToInt(IO.readln("Enter your choice: "));
        String stateMessage;


        if (stateChoice == 1) {
            stateMessage = smartEquipmentHandlerService.setSmartEquipmentState(
                    smartEquipment,
                    true,
                    isExtraState);

        } else if (stateChoice == 2) {
            stateMessage = smartEquipmentHandlerService.setSmartEquipmentState(
                    smartEquipment,
                    false,
                    isExtraState);
        } else if (stateChoice == 3) {
            return "State change aborted\n";
        } else {
            return "Invalid choice\n";
        }

        return String.format("Changed %s state: %s: %s", stateType, smartEquipment.getName(), stateMessage);

    }
}