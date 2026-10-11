package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import org.junit.jupiter.api.Test;
import se.chasacademy.fredrikhallstrom.mysmarthome.testutils.FixedClockForTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LampTest implements FixedClockForTest {
    @Test
    public void shouldReturnLampIsOffWhenNewLampTest() {
        // Arrange
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));

        // Act
        List<String> states = lamp.getStateMessages();

        // Assert
        assertEquals(List.of("lamp is off"), states);
    }

    @Test
    public void shouldReturnLampIsOnWhenActivatedTest() {
        // Arrange
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        lamp.setStateActive(LocalDateTime.now(FIXED_CLOCK));

        // Act
        List<String> states = lamp.getStateMessages();

        // Assert
        assertEquals(List.of("lamp is on"), states);
    }

    @Test
    public void shouldReturnLampIsOffAfterActivateAndDeactivateTest() {
        // Arrange
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        lamp.setStateActive(LocalDateTime.now(FIXED_CLOCK));
        lamp.setStateInactive(LocalDateTime.now(FIXED_CLOCK));

        // Act
        List<String> states = lamp.getStateMessages();

        // Assert
        assertEquals(List.of("lamp is off"), states);
    }
}
