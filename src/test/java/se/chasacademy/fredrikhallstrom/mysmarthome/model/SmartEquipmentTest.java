package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;

public class SmartEquipmentTest {
    // Setup using subclass because of abstract class test
    static Lamp instancedSubClass;

    public static void setUpForSubclassInstance() {
        Clock clock = Clock.systemUTC();
        instancedSubClass = new Lamp("fakeLamp", clock);
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
