package se.chasacademy.fredrikhallstrom.mysmarthome;

import se.chasacademy.fredrikhallstrom.mysmarthome.demomysmarthome.DemoMySmartHome;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepositoryInterface;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype.InMemoryRepository;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SmartEquipmentSearchService;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.BaseForMenu;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.*;

import java.time.Clock;
import java.util.Arrays;

import static se.chasacademy.fredrikhallstrom.mysmarthome.ui.ascii.WelcomeLogo.welcomeLogo;

public class Main {
    void main(String[] args) {
        SmartEquipmentRepositoryInterface smartEquipmentRepository = new InMemoryRepository();
        Clock clock = Clock.systemUTC();

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(clock);
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