package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentaddremove;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CreationOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.InvalidSmartEquipmentNameException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentIdCantBeNegativeException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipmentType;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.util.InputValidate;

class OptionAddSmartEquipment implements InputValidate {
    private final SmartEquipmentService smartEquipmentService;

    OptionAddSmartEquipment(SmartEquipmentService smartEquipmentService) {
        this.smartEquipmentService = smartEquipmentService;
    }

    void addSmartEquipment() {
        try {
            String optionString = "Add SmartEquipment";
            IO.println(optionString);
            IO.println("*".repeat(optionString.length()) + "\n");

            IO.println("What kind of SmartEquipment do you want to add?\n");
            int index = 1;
            for (SmartEquipmentType name : SmartEquipmentType.values()) {
                IO.println(index + ". " + name.getDisplayName());
                index++;
            }
            IO.println((SmartEquipmentType.values().length + 1) + ". Abort");

            int choiceInt = validateInputStringToInt(IO.readln("\nChoice: "));
            IO.println("");

            if (choiceInt == SmartEquipmentType.values().length + 1) {
                return;
            }

            index = 1;
            SmartEquipmentType typeChoice = null;
            for (SmartEquipmentType type : SmartEquipmentType.values()) {
                if (index == choiceInt) {
                    typeChoice = type;
                    break;
                } else {
                    index++;
                }
            }
            if (typeChoice == null) {
                IO.println("Something went wrong, try again!");
                return;
            }

            IO.println(String.format("What name do you want on your %s?", typeChoice.getDisplayName()));


            while (true) {
                String choiceName;
                try {
                    choiceName = IO.readln("Name?: ");
                    if (choiceName == null) {
                        return;
                    }

                    SmartEquipment createdSmartEquipment = smartEquipmentService.createSmartEquipment(choiceName, typeChoice);

                    IO.println("\nSmartEquipment was created:\n\n" + createdSmartEquipment + "\n");
                    return;

                } catch (InvalidSmartEquipmentNameException error) {
                    IO.println(error.getMessage());
                }
            }

        } catch (CreationOfSmartEquipmentDidFailSomewhereException |
                 MustBeANumberException |
                 SmartEquipmentIdCantBeNegativeException error) {

            IO.println(error.getMessage());

        }
    }
}
