package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentlist;

import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentServiceInterface;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;

import java.util.LinkedHashMap;

public class MenuSmartEquipmentList extends BaseForMenu {
    private final OptionListSmartEquipment optionListSmartEquipment;

    public MenuSmartEquipmentList(SmartEquipmentServiceInterface smartEquipmentService) {
        this.optionListSmartEquipment = new OptionListSmartEquipment(smartEquipmentService);
    }

    public void menu() {
        menuItemsMap = new LinkedHashMap<>();

        menuItemsMap.put("List All SmartEquipment Menu", () -> {
        });

        menuItemsMap.put("List All SmartEquipment in order by Id",
                optionListSmartEquipment::listSmartEquipmentSortedById);

        super.menu(menuItemsMap);

    }



}
