package se.chasacademy.fredrikhallstrom.mysmarthome.demomysmarthome;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.FoundNoSmartEquipmentByIdException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.InvalidSmartEquipmentNameException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipmentType;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype.InMemoryRepository;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentServiceInterface;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SearchSortingType;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.search.SmartEquipmentSearchService;

import java.time.Clock;

public class DemoMySmartHome {
    private final Clock clock;

    public DemoMySmartHome(Clock clock) {
        this.clock = clock;
    }

    public void run() {
        SmartEquipmentServiceInterface smartEquipmentService =
                new SmartEquipmentService(new InMemoryRepository(), clock);

        SmartEquipmentHandlerService smartEquipmentHandlerService =
                new SmartEquipmentHandlerService(smartEquipmentService, clock);

        SmartEquipmentSearchService smartEquipmentSearchService =
                new SmartEquipmentSearchService(smartEquipmentService);

        newDemoPointPause("1. Creating several SmartEquipment");

        FakeRepoInit.init(smartEquipmentService, smartEquipmentHandlerService);

        SmartEquipment lamp = smartEquipmentService.createSmartEquipment(
                "Kitchen lamp",
                SmartEquipmentType.LAMP
        );

        SmartEquipment camera = smartEquipmentService.createSmartEquipment(
                "Garage camera",
                SmartEquipmentType.CAMERA
        );

        SmartEquipment radiator = smartEquipmentService.createSmartEquipment(
                "Office radiator",
                SmartEquipmentType.RADIATOR
        );

        IO.println("Created: " + lamp);
        IO.println("Created: " + camera);
        IO.println("Created: " + radiator);

        newDemoPointPause("2. Activate and deactivate");
        IO.println(lamp.getName() +
                ": " +
                smartEquipmentHandlerService.setSmartEquipmentState(
                        lamp, true,
                        false)
        );

        IO.println(lamp.getName() +
                ": " +
                smartEquipmentHandlerService.setSmartEquipmentState(
                        lamp,
                        false,
                        false)
        );

        IO.println(camera.getName() +
                ": " +
                smartEquipmentHandlerService.setSmartEquipmentState(
                        camera,
                        true,
                        false)
        );

        IO.println(camera.getName() +
                ": " +
                smartEquipmentHandlerService.setSmartEquipmentState(
                        camera,
                        true,
                        true)
        );

        IO.println(radiator.getName() +
                ": " +
                smartEquipmentHandlerService.setSmartEquipmentState(
                        radiator,
                        true,
                        false)
        );

        newDemoPointPause("3. Search by name \"room\" (ascending)");
        for (SmartEquipment found : smartEquipmentSearchService.searchSmartEquipmentByName(
                "room",
                SearchSortingType.ASCENDING)) {

            IO.println(found);
        }

        newDemoPointPause("4. List all SmartEquipment");
        for (SmartEquipment smartEquipment : smartEquipmentService.getAllSmartEquipmentsSortedMapById().values()) {
            IO.println(smartEquipment);
        }

        newDemoPointPause("5. Error handling");
        try {
            smartEquipmentHandlerService.setSmartEquipmentState(lamp, false, false);
        } catch (CantDeactivateAlreadyDeactivatedStateException error) {
            IO.println("Caught: " + error.getMessage());
        }
        try {
            smartEquipmentService.createSmartEquipment("Kitchen lamp", SmartEquipmentType.LAMP);
        } catch (InvalidSmartEquipmentNameException error) {
            IO.println("Caught: " + error.getMessage());
        }
        try {
            smartEquipmentSearchService.searchSmartEquipmentById(999);
        } catch (FoundNoSmartEquipmentByIdException error) {
            IO.println("Caught: " + error.getMessage());
        }

        newDemoPointPause("Demonstration finished");
    }

    private void newDemoPointPause(String demoPointString) {
        try {
            int pauseLengthInMilliSeconds = 2000;
            Thread.sleep(pauseLengthInMilliSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        IO.println("\n" + demoPointString);
        IO.println("*".repeat(demoPointString.length()));
    }
}

