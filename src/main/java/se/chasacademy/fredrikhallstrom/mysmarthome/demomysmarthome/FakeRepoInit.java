package se.chasacademy.fredrikhallstrom.mysmarthome.demomysmarthome;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.Camera;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Radiator;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;

import java.time.LocalDateTime;

public class FakeRepoInit {

    public static void init(SmartEquipmentService smartEquipmentService, SmartEquipmentHandlerService smartEquipmentHandlerService) {
        LocalDateTime timeRightNow = smartEquipmentService.getCurrentLocalDateTime();
        
        Lamp lamp = new Lamp("Living room Lamp 1", timeRightNow.minusDays(7));
        Lamp lamp2 = new Lamp("Living room Lamp 2", timeRightNow.minusDays(5));
        Camera camera = new Camera("Hallway Camera", timeRightNow.minusDays(3));
        Radiator radiator = new Radiator("Bed room Radiator 1", timeRightNow.minusDays(1));
        Camera camera2 = new Camera("Front door Camera", timeRightNow.minusDays(2));
        Radiator radiator2 = new Radiator("Bed room Radiator 2", timeRightNow);

        smartEquipmentService.addSmartEquipment(lamp);
        smartEquipmentService.addSmartEquipment(lamp2);
        smartEquipmentService.addSmartEquipment(camera);
        smartEquipmentService.addSmartEquipment(radiator);
        smartEquipmentService.addSmartEquipment(camera2);
        smartEquipmentService.addSmartEquipment(radiator2);

        smartEquipmentHandlerService.setSmartEquipmentState(lamp2, true, false);
        smartEquipmentHandlerService.setSmartEquipmentState(camera, true, false);
    }

}
