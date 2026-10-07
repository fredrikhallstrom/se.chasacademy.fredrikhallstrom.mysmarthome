package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.handler.SmartEquipmentHandlerService;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;

public class SmartEquipmentHandlerServiceTest {
    @Test
    public void shouldCallMethodSetStateActiveOfSmartEquipmentWhenTurnStateOnIsTrue() {
        // Arrange
        Clock clock = Clock.systemUTC();
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService();
        int fakeId = 1;

        Lamp fakeLamp = new Lamp("fakeLamp", clock);
        fakeLamp.setId(fakeId);
        if (fakeLamp.isStateActive()) {
            fakeLamp.setStateInactive();
        }
        
        // Act
        smartEquipmentHandlerService.setSmartEquipmentState(fakeLamp, true);

        // Assert
        assertTrue(fakeLamp.isStateActive());
    }

    @Test
    public void shouldCallMethodSetStateInactiveOfSmartEquipmentWhenTurnStateOnIsFalse() {
        // Arrange
        Clock clock = Clock.systemUTC();
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService();
        int fakeId = 1;

        Lamp fakeLamp = new Lamp("fakeLamp", clock);
        fakeLamp.setId(fakeId);
        if (!fakeLamp.isStateActive()) {
            fakeLamp.setStateActive();
        }

        // Act
        smartEquipmentHandlerService.setSmartEquipmentState(fakeLamp, false);

        // Assert
        assertFalse(fakeLamp.isStateActive());
    }

    @Test
    public void shouldThrowCantActiveAlreadyActivatedEquipmentExceptionIfAlreadySmartEquipmentActivated() {
        // Arrange
        Clock clock = Clock.systemUTC();
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService();
        int fakeId = 1;

        Lamp fakeLamp = new Lamp("fakeLamp", clock);
        fakeLamp.setId(fakeId);
        if (!fakeLamp.isStateActive()) {
            fakeLamp.setStateActive();
        }
    
        // Act
        Executable throwAct = () ->
                smartEquipmentHandlerService.setSmartEquipmentState(fakeLamp, true);
    
        // Assert
        assertThrows(CantActiveAlreadyActivatedEquipmentException.class, throwAct);
    
    }

    @Test
    public void shouldThrowCantDeactivateAlreadyDeactivatedEquipmentExceptionIfAlreadySmartEquipmentDeactivated() {
        // Arrange
        Clock clock = Clock.systemUTC();
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService();
        int fakeId = 1;

        Lamp fakeLamp = new Lamp("fakeLamp", clock);
        fakeLamp.setId(fakeId);
        if (fakeLamp.isStateActive()) {
            fakeLamp.setStateInactive();
        }

        // Act
        Executable throwAct = () ->
                smartEquipmentHandlerService.setSmartEquipmentState(fakeLamp, false);

        // Assert
        assertThrows(CantDeactivateAlreadyDeactivatedEquipmentException.class, throwAct);

    }


}
