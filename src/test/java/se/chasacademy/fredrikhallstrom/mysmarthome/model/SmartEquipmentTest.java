package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedEquipmentException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedEquipmentException;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

public class SmartEquipmentTest {
    // Setup using subclass because of abstract class test
    static Lamp instancedSubClass;
    static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2023-01-01T00:00:00Z"), ZoneOffset.UTC);

    public static void setUpForSubclassInstanceWithFixedClock() {
        instancedSubClass = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));
    }

    @Test
    public void shouldSetStateActiveTest() {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();

        if (instancedSubClass.isStateActive()) {
            instancedSubClass.setStateInactive(LocalDateTime.now(FIXED_CLOCK));
        }

        // Act
        instancedSubClass.setStateActive(LocalDateTime.now(FIXED_CLOCK));

        // Assert
        assertTrue(instancedSubClass.isStateActive());
    }

    @Test
    public void shouldReturnStringFromMethodLightOnInLightableInterfaceTest() {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();

        if (instancedSubClass.isStateActive()) {
            instancedSubClass.setStateInactive(LocalDateTime.now(FIXED_CLOCK));
        }

        // Act
        String returnedString = instancedSubClass.setStateActive(LocalDateTime.now(FIXED_CLOCK));

        // Assert
        assertEquals(instancedSubClass.lightOn(), returnedString);

    }

    @Test
    public void shouldReturnStringFromMethodLightOffInLightableInterfaceTest() {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();

        if (!instancedSubClass.isStateActive()) {
            instancedSubClass.setStateActive(LocalDateTime.now(FIXED_CLOCK));
        }

        // Act
        String returnedString = instancedSubClass.setStateInactive(LocalDateTime.now(FIXED_CLOCK));

        // Assert
        assertEquals(instancedSubClass.lightOff(), returnedString);

    }
    @Test
    public void shouldThrowCantActiveAlreadyActivatedEquipmentTest() {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();

        if (!instancedSubClass.isStateActive()) {
            instancedSubClass.setStateActive(LocalDateTime.now(FIXED_CLOCK));
        }

        // Act
        Executable throwAct = () -> instancedSubClass.setStateActive(LocalDateTime.now(FIXED_CLOCK));

        // Assert
        assertThrows(CantActiveAlreadyActivatedEquipmentException.class, throwAct);
    }

    @Test
    public void shouldSetStateInactiveTest() {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();

        if (!instancedSubClass.isStateActive()) {
            instancedSubClass.setStateActive(LocalDateTime.now(FIXED_CLOCK));
        }

        // Act
        instancedSubClass.setStateInactive(LocalDateTime.now(FIXED_CLOCK));

        // Assert
        assertFalse(instancedSubClass.isStateActive());
    }

    @Test
    public void shouldThrowCantDeactivateAlreadyDeactivatedEquipmentTest() {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();

        if (instancedSubClass.isStateActive()) {
            instancedSubClass.setStateInactive(LocalDateTime.now(FIXED_CLOCK));
        }

        // Act
        Executable throwAct = () -> instancedSubClass.setStateInactive(LocalDateTime.now(FIXED_CLOCK));

        // Assert
        assertThrows(CantDeactivateAlreadyDeactivatedEquipmentException.class, throwAct);

    }

}
