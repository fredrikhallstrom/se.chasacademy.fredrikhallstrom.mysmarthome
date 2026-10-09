package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 2000, 10000, 100000})
    public void shouldSetIdWhenCalledOnSmartEquipmentTest(int id) {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();

        // Act
        instancedSubClass.setId(id);

        // Assert
        assertEquals(id, instancedSubClass.getId());

    }

    @Test
    public void shouldSetLowestPossibleIdWhenCalledOnSmartEquipmentTest() {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();

        int newId = 0; // Lowest possible id

        // Act
        instancedSubClass.setId(newId);

        // Assert
        assertEquals(newId, instancedSubClass.getId());

    }

    @Test
    public void shouldSetHighestPossibleIdWhenCalledOnSmartEquipmentTest() {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();
        int highestPossibleId = Integer.MAX_VALUE;

        // Act
        instancedSubClass.setId(highestPossibleId);

        // Assert
        assertEquals(highestPossibleId, instancedSubClass.getId());

    }


    @Test
    public void shouldNotReSetIdWhenIdAlreadySetTest() {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();

        int defaultId = instancedSubClass.getId();
        int newId = defaultId + 1;
        int reSetId = newId + 1;
        instancedSubClass.setId(newId);


        // Act
        instancedSubClass.setId(reSetId);

        // Assert
        assertEquals(newId, instancedSubClass.getId());

    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenIdIsNegativeTest() {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();
        int negativeId = -5;

        // Act
        Executable throwAct = () -> instancedSubClass.setId(negativeId);

        // Assert
        assertThrows(IllegalArgumentException.class, throwAct);
    }


}
