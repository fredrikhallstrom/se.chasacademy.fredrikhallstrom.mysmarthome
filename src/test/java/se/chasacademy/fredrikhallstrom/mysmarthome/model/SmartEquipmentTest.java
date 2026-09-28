package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SmartEquipmentTest {
    // Setup using instancable class because of abstract class test
    // Also wrote setup for tests with use of mocked clock

    static Lamp instancedSubClass;
    static Clock installationClock;

    public static void setUpForClockMockingWithInstance() {
        installationClock = mock(Clock.class);

        // Std clock is now.
        when(installationClock.instant()).thenReturn(Instant.now());
        when(installationClock.getZone()).thenReturn(ZoneId.systemDefault());

        LocalDateTime installationTime = LocalDateTime.now(installationClock);
        instancedSubClass = new Lamp("fakeLamp", installationTime);
    }

    public static void setUpForSubclassInstance() {
        instancedSubClass = new Lamp("fakeLamp", LocalDateTime.now());

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
}
