package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.testutils.FixedClockForTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CameraTest implements FixedClockForTest {
    @Test
    public void shouldReturnRecordingOffAndLampOffWhenNewCameraTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));

        // Act
        List<String> states = camera.getStateMessages();

        // Assert
        assertEquals(List.of("recording is off", "lamp is off"), states);
    }

    @Test
    public void shouldReturnRecordingOnWhenActivatedTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));
        camera.setStateActive(LocalDateTime.now(FIXED_CLOCK));

        // Act
        List<String> states = camera.getStateMessages();

        // Assert
        assertEquals(List.of("recording is on", "lamp is off"), states);
    }

    @Test
    public void shouldReturnLampOnWhileNotRecordingTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));
        camera.setExtraStateOn();

        // Act
        List<String> states = camera.getStateMessages();

        // Assert
        assertEquals(List.of("recording is off", "lamp is on"), states);
    }

    @Test
    public void shouldReturnBothOnWhenRecordingAndLampOnTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));
        camera.setStateActive(LocalDateTime.now(FIXED_CLOCK));
        camera.setExtraStateOn();

        // Act
        List<String> states = camera.getStateMessages();

        // Assert
        assertEquals(List.of("recording is on", "lamp is on"), states);
    }

    @Test
    public void shouldReturnLampIsOnFromLightStateStringWhenLightOnTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));
        camera.setExtraStateOn();

        // Act
        String lightState = camera.getExtraStateString();

        // Assert
        assertEquals("lamp is on", lightState);
    }

    @Test
    public void shouldReturnLampIsOffFromLightStateStringWhenNewCameraTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));

        // Act
        String lightState = camera.getExtraStateString();

        // Assert
        assertEquals("lamp is off", lightState);
    }

    @Test
    public void shouldReturnLampIsOffFromLightStateStringAfterLightOnAndOffTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));
        camera.setExtraStateOn();
        camera.setExtraStateOff();

        // Act
        String lightState = camera.getExtraStateString();

        // Assert
        assertEquals("lamp is off", lightState);
    }

    @Test
    public void shouldNotChangeLightStateWhenRecordingStartsTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));
        camera.setExtraStateOn();
        camera.setStateActive(LocalDateTime.now(FIXED_CLOCK));

        // Act
        String lightState = camera.getExtraStateString();

        // Assert
        assertEquals("lamp is on", lightState);
    }

    @Test
    public void shouldReturnLampIsOnWhenExtraStateTurnedOnTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));

        // Act
        String message = camera.setExtraStateOn();

        // Assert
        assertEquals("lamp is on", message);
    }

    @Test
    public void shouldReturnLampIsOffWhenExtraStateTurnedOffTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));
        camera.setExtraStateOn();

        // Act
        String message = camera.setExtraStateOff();

        // Assert
        assertEquals("lamp is off", message);
    }

    @Test
    public void shouldNotChangeRecordingStateWhenExtraStateTurnedOnTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));

        // Act
        camera.setExtraStateOn();

        // Assert
        assertFalse(camera.isStateActive());
    }

    @Test
    public void shouldThrowCantActiveAlreadyActivatedStateExceptionWhenExtraStateAlreadyOnTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));
        camera.setExtraStateOn();

        // Act
        Executable throwAct = camera::setExtraStateOn;

        // Assert
        assertThrows(CantActiveAlreadyActivatedStateException.class, throwAct);
    }

    @Test
    public void shouldThrowCantDeactivateAlreadyDeactivatedStateExceptionWhenExtraStateAlreadyOffTest() {
        // Arrange
        Camera camera = new Camera("Entrance", LocalDateTime.now(FIXED_CLOCK));

        // Act
        Executable throwAct = camera::setExtraStateOff;

        // Assert
        assertThrows(CantDeactivateAlreadyDeactivatedStateException.class, throwAct);
    }
}
