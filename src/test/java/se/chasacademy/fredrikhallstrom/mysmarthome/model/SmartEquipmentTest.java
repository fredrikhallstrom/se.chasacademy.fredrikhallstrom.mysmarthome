package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentIdCantBeNegativeException;
import se.chasacademy.fredrikhallstrom.mysmarthome.testutils.FixedClockForTest;
import se.chasacademy.fredrikhallstrom.mysmarthome.util.TimeFormat;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class SmartEquipmentTest implements FixedClockForTest {
    // Setup using subclass because of abstract class test
    static Lamp instancedSubClass;

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
        assertThrows(CantActiveAlreadyActivatedStateException.class, throwAct);
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
        assertThrows(CantDeactivateAlreadyDeactivatedStateException.class, throwAct);

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
    public void shouldThrowSmartEquipmentIdCantBeNegativeExceptionWhenIdIsNegativeTest() {
        // Arrange
        setUpForSubclassInstanceWithFixedClock();
        int negativeId = -5;

        // Act
        Executable throwAct = () -> instancedSubClass.setId(negativeId);

        // Assert
        assertThrows(SmartEquipmentIdCantBeNegativeException.class, throwAct);
    }

    @Test
    public void shouldSetLastActivationTimeToGivenTimeTest() {
        // Arrange
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        LocalDateTime activationTime = LocalDateTime.now(FIXED_CLOCK).plusHours(1);

        // Act
        lamp.setStateActive(activationTime);

        // Assert
        assertEquals(activationTime, lamp.getLastActivationTime());
    }

    @Test
    public void shouldSetLastDeactivationTimeToGivenTimeTest() {
        // Arrange
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        lamp.setStateActive(LocalDateTime.now(FIXED_CLOCK).plusHours(1));
        LocalDateTime deactivationTime = LocalDateTime.now(FIXED_CLOCK).plusHours(2);

        // Act
        lamp.setStateInactive(deactivationTime);

        // Assert
        assertEquals(deactivationTime, lamp.getLastDeactivationTime());
    }

    @Test
    public void shouldBeActiveWithLatestTimeAfterOnOffOnCycleTest() {
        // Arrange
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        lamp.setStateActive(LocalDateTime.now(FIXED_CLOCK).plusHours(1));
        lamp.setStateInactive(LocalDateTime.now(FIXED_CLOCK).plusHours(2));
        LocalDateTime secondActivationTime = LocalDateTime.now(FIXED_CLOCK).plusHours(3);

        // Act
        lamp.setStateActive(secondActivationTime);

        // Assert
        assertTrue(lamp.isStateActive());
        assertEquals(secondActivationTime, lamp.getLastActivationTime());
    }

    @Test
    public void shouldReturnOneLineStringForLampTest() {
        // Arrange
        TimeFormat timeFormat = new TimeFormat() {};
        LocalDateTime installationTime = LocalDateTime.now(FIXED_CLOCK);
        Lamp lamp = new Lamp("Kitchen", installationTime);
        lamp.setId(1000);
        String formattedTime = timeFormat.formatLocalDateTimeAndTimeZoneForSystemDefault(installationTime);

        // Act
        String result = lamp.toString();

        // Assert
        assertEquals("Type: Lamp, ID: 1000, Kitchen(Installed: " + formattedTime
                + ") - Current state: lamp is off @ " + formattedTime, result);
    }

    @Test
    public void shouldReturnOtherStatesOnSecondLineForCameraTest() {
        // Arrange
        TimeFormat timeFormat = new TimeFormat() {};
        LocalDateTime installationTime = LocalDateTime.now(FIXED_CLOCK);
        Camera camera = new Camera("Entrance", installationTime);
        camera.setId(1001);
        String formattedTime = timeFormat.formatLocalDateTimeAndTimeZoneForSystemDefault(installationTime);

        // Act
        String result = camera.toString();

        // Assert
        assertEquals("Type: Camera, ID: 1001, Entrance(Installed: " + formattedTime
                + ") - Current state: recording is off @ " + formattedTime
                + "\n\tOther state/-s: lamp is off", result);
    }

    @Test
    public void shouldShowDeactivationTimeWhenActivationAndDeactivationTimeAreEqualTest() {
        // Arrange
        TimeFormat timeFormat = new TimeFormat() {};
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        LocalDateTime sameTime = LocalDateTime.now(FIXED_CLOCK).plusHours(1);
        lamp.setStateActive(sameTime);
        lamp.setStateInactive(sameTime);

        // Act
        String result = lamp.toString();

        // Assert
        assertTrue(result.endsWith("lamp is off @ " + timeFormat.formatLocalDateTimeAndTimeZoneForSystemDefault(sameTime)));
    }

    @Test
    public void shouldShowIdMinusOneBeforeSavedInRepositoryTest() {
        // Arrange
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));

        // Act
        String result = lamp.toString();

        // Assert
        assertTrue(result.startsWith("Type: Lamp, ID: -1, Kitchen"));
    }
}
