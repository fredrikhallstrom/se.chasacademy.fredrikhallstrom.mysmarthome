package se.chasacademy.fredrikhallstrom.mysmarthome;

import se.chasacademy.fredrikhallstrom.mysmarthome.demomysmarthome.DemoMySmartHome;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype.InMemoryRepository;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SmartEquipmentSearchService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.*;

import java.util.Arrays;

import static se.chasacademy.fredrikhallstrom.mysmarthome.ui.ascii.WelcomeLogo.welcomeLogo;

public class Main {
    void main(String[] args) {
        SmartEquipmentRepository smartEquipmentRepository = new InMemoryRepository();

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService();
        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(
                smartEquipmentService
        );

        if (Arrays.toString(args).contains("demo")) {
            DemoMySmartHome.menu();
        } else {
            BaseForMenu menuMain = new MenuMain(
                    smartEquipmentService,
                    smartEquipmentHandlerService,
                    smartEquipmentSearchService
            );

            IO.println(welcomeLogo);
            menuMain.menu();
        }

    }
}