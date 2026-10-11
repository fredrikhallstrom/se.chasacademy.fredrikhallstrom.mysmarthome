package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentaddremove;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.FoundNoSmartEquipmentByIdException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentIdCantBeNegativeException;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;

class OptionRemoveSmartEquipment {
    private final SmartEquipmentService smartEquipmentService;

    OptionRemoveSmartEquipment(SmartEquipmentService smartEquipmentService) {
        this.smartEquipmentService = smartEquipmentService;
    }

    void removeSmartEquipment() {
        String optionString = "Remove SmartEquipment";
        IO.println(optionString);
        IO.println("*".repeat(optionString.length()) + "\n");

        while (true) {
            IO.println("Enter the id of the SmartEquipment you want to remove:");
            String smartEquipmentIdString = IO.readln("ID: ");
            if (smartEquipmentIdString == null) {
                return;
            }

            try {
                boolean removed = smartEquipmentService.removeSmartEquipment(smartEquipmentIdString);
                if (removed) {
                    IO.println("SmartEquipment removed successfully.");
                    break;
                }
            } catch (MustBeANumberException | SmartEquipmentIdCantBeNegativeException error) {
                IO.println(error.getMessage());
            } catch (RemoveOfSmartEquipmentDidFailSomewhereException | FoundNoSmartEquipmentByIdException error) {
                IO.println(error.getMessage());
                break;
            }
        }

    }
}
