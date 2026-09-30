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
        instancedSubClass = new Lamp("fakeLamp", LocalDateTime.now(clock));
    }

    public static void setUpForSubclassInstanceWithMockedClock() {
        Clock clock = mock(Clock.class);
        instancedSubClass = new Lamp("fakeLamp", LocalDateTime.now(clock));
    }

    @Test
    public void shouldSetStateActiveTest() {
        // Arrange
        setUpForSubclassInstance();

        // Act
        if (instancedSubClass.isStateActive()) {
            instancedSubClass.setStateInactive();
        }
        instancedSubClass.setStateActive();

        // Assert
        assertTrue(instancedSubClass.isStateActive());
    }

    @Test
    public void shouldThrowCantActiveAlreadyActivatedEquipmentTest() {
        // Arrange
        setUpForSubclassInstance();

        // Act
        if (!instancedSubClass.isStateActive()) {
            instancedSubClass.setStateActive();
        }

        Executable throwAct = () -> instancedSubClass.setStateActive();

        // Assert
        assertThrows(CantActiveAlreadyActivatedEquipmentException.class, throwAct);
    }

    @Test
    public void shouldSetStateInactiveTest() {
        // Arrange
        setUpForSubclassInstance();

        // Act
        if (!instancedSubClass.isStateActive()) {
            instancedSubClass.setStateActive();
        }

        instancedSubClass.setStateInactive();

        // Assert
        assertFalse(instancedSubClass.isStateActive());
    }

    @Test
    public void shouldThrowCantDeactivateAlreadyDeactivatedEquipmentTest() {
        // Arrange
        setUpForSubclassInstance();

        // Act
        if (instancedSubClass.isStateActive()) {
            instancedSubClass.setStateInactive();
        }

        Executable throwAct = () -> instancedSubClass.setStateInactive();

        // Assert
        assertThrows(CantDeactivateAlreadyDeactivatedEquipmentException.class, throwAct);

    }

    @Test
    public void shouldIncreaseUniqueIdByOneWhenEquipmentCreatedTest() {
        // Arrange
        setUpForSubclassInstance();
        int idBefore = instancedSubClass.getId();

        // Act
        Lamp lamp2 = new Lamp("fakeLamp2", LocalDateTime.now());
        int idAfter = lamp2.getId();

        // Assert
        assertEquals(1, idAfter - idBefore);

    }
}
