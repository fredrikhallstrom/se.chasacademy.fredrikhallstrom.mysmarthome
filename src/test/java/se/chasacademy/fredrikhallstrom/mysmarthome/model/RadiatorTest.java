package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import org.junit.jupiter.api.Test;
import se.chasacademy.fredrikhallstrom.mysmarthome.testutils.FixedClockForTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RadiatorTest implements FixedClockForTest {
    @Test
    public void shouldReturnHeatingIsOffWhenNewRadiatorTest() {
        // Arrange
        Radiator radiator = new Radiator("Hallway", LocalDateTime.now(FIXED_CLOCK));

        // Act
        List<String> states = radiator.getStateMessages();

        // Assert
        assertEquals(List.of("heating is off"), states);
    }

    @Test
    public void shouldReturnHeatingIsOnWhenActivatedTest() {
        // Arrange
        Radiator radiator = new Radiator("Hallway", LocalDateTime.now(FIXED_CLOCK));
        radiator.setStateActive(LocalDateTime.now(FIXED_CLOCK));

        // Act
        List<String> states = radiator.getStateMessages();

        // Assert
        assertEquals(List.of("heating is on"), states);
    }

    @Test
    public void shouldReturnHeatingIsOffAfterActivateAndDeactivateTest() {
        // Arrange
        Radiator radiator = new Radiator("Hallway", LocalDateTime.now(FIXED_CLOCK));
        radiator.setStateActive(LocalDateTime.now(FIXED_CLOCK));
        radiator.setStateInactive(LocalDateTime.now(FIXED_CLOCK));

        // Act
        List<String> states = radiator.getStateMessages();

        // Assert
        assertEquals(List.of("heating is off"), states);
    }
}
