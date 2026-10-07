package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SmartEquipmentTest {
    // Setup using instancable class because of abstract class test
    static Lamp instancedSubClass;

    public static void setUpForSubclassInstance() {
        Clock clock = Clock.systemUTC();
        instancedSubClass = new Lamp("fakeLamp", LocalDateTime.now(clock), clock);
    }

    public static void setUpForSubclassInstanceWithMockedClock() {
        Clock clock = mock(Clock.class);
        instancedSubClass = new Lamp("fakeLamp", LocalDateTime.now(clock), clock);
    }

    @Test
    public void shouldSetStateActiveTest() {
        // Arrange
        setUpForSubclassInstance();

        if (instancedSubClass.isStateActive()) {
            instancedSubClass.setStateInactive();
        }

        // Act
        instancedSubClass.setStateActive();

        // Assert
        assertTrue(instancedSubClass.isStateActive());
    }

    @Test
    public void shouldReturnStringFromMethodLightOnInLightableInterfaceTest() {
        // Arrange
        setUpForSubclassInstance();

        if (instancedSubClass.isStateActive()) {
            instancedSubClass.setStateInactive();
        }

        // Act
        String returnedString = instancedSubClass.setStateActive();

        // Assert
        assertEquals(instancedSubClass.lightOn(), returnedString);

    }

    @Test
    public void shouldReturnStringFromMethodLightOffInLightableInterfaceTest() {
        // Arrange
        setUpForSubclassInstance();

        if (!instancedSubClass.isStateActive()) {
            instancedSubClass.setStateActive();
        }

        // Act
        String returnedString = instancedSubClass.setStateInactive();

        // Assert
        assertEquals(instancedSubClass.lightOff(), returnedString);

    }
    @Test
    public void shouldThrowCantActiveAlreadyActivatedEquipmentTest() {
        // Arrange
        setUpForSubclassInstance();

        if (!instancedSubClass.isStateActive()) {
            instancedSubClass.setStateActive();
        }

        // Act
        Executable throwAct = () -> instancedSubClass.setStateActive();

        // Assert
        assertThrows(CantActiveAlreadyActivatedEquipmentException.class, throwAct);
    }

    @Test
    public void shouldSetStateInactiveTest() {
        // Arrange
        setUpForSubclassInstance();

        if (!instancedSubClass.isStateActive()) {
            instancedSubClass.setStateActive();
        }

        // Act
        instancedSubClass.setStateInactive();

        // Assert
        assertFalse(instancedSubClass.isStateActive());
    }

    @Test
    public void shouldThrowCantDeactivateAlreadyDeactivatedEquipmentTest() {
        // Arrange
        setUpForSubclassInstance();

        if (instancedSubClass.isStateActive()) {
            instancedSubClass.setStateInactive();
        }

        // Act
        Executable throwAct = () -> instancedSubClass.setStateInactive();

        // Assert
        assertThrows(CantDeactivateAlreadyDeactivatedEquipmentException.class, throwAct);

    }

}
