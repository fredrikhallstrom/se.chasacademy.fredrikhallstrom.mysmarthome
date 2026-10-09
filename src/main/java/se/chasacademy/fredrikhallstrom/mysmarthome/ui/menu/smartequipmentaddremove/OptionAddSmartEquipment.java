package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentaddremove;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CreationOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipmentType;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;

class OptionAddSmartEquipment {
    private final SmartEquipmentService smartEquipmentService;

    OptionAddSmartEquipment(SmartEquipmentService smartEquipmentService) {
        this.smartEquipmentService = smartEquipmentService;
    }

    void addSmartEquipment() throws MustBeANumberException {
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

        String choiceString = IO.readln("\nChoice: ");
        IO.println("");
        int choiceInt;
        try {
            choiceInt = Integer.parseInt(choiceString);
        } catch (NumberFormatException e) {
            throw new MustBeANumberException();
        }

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

        String choiceName = "";
        boolean emptyName = true;
        while (emptyName) {
            choiceName = IO.readln("Name?: ");
            if (choiceName.isBlank()) {
                IO.println("Name can't be blank!");
            } else {
                emptyName = false;
            }
        }
        try {
            SmartEquipment createdSmartEquipment = smartEquipmentService.addSmartEquipment(typeChoice.getCreator().create(choiceName, smartEquipmentService.getCurrentLocalDateTime()));
            IO.println(
                    "\n" +
                    "SmartEquipment was created:\n\n" +
                    createdSmartEquipment +
                    "\n"
            );

        } catch (CreationOfSmartEquipmentDidFailSomewhereException error) {
            IO.println(error.getMessage());
        }
    }
}
