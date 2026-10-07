package se.chasacademy.fredrikhallstrom.mysmarthome;

import se.chasacademy.fredrikhallstrom.mysmarthome.demoMySmartHome.DemoMySmartHome;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype.InMemoryRepository;
import se.chasacademy.fredrikhallstrom.mysmarthome.ui.MainMenu;

import java.util.Arrays;

public class Main {
    void main(String[] args) {
        SmartEquipmentRepository smartEquipmentRepository = new InMemoryRepository();

        if (Arrays.toString(args).contains("demo")) {
            DemoMySmartHome.main();
        } else {
            MainMenu mainMenu = new MainMenu();
            mainMenu.mainMenu();
        }

    }
}