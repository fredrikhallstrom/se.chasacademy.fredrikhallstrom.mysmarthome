package se.chasacademy.fredrikhallstrom.mysmarthome.service.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentHasNoExtraStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Camera;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.testutils.FixedClockForTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class SmartEquipmentHandlerServiceTest implements FixedClockForTest {
    @Test
    public void shouldCallMethodSetStateActiveOfSmartEquipmentWhenTurnStateOnIsTrue() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        int fakeId = 1;

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp.setId(fakeId);
        if (fakeLamp.isStateActive()) {
            fakeLamp.setStateInactive(LocalDateTime.now(FIXED_CLOCK));
        }
        
        // Act
        smartEquipmentHandlerService.setSmartEquipmentState(fakeLamp, true, false);

        // Assert
        assertTrue(fakeLamp.isStateActive());
    }

    @Test
    public void shouldCallMethodSetStateInactiveOfSmartEquipmentWhenTurnStateOnIsFalse() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        int fakeId = 1;

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp.setId(fakeId);
        if (!fakeLamp.isStateActive()) {
            fakeLamp.setStateActive(LocalDateTime.now(FIXED_CLOCK));
        }

        // Act
        smartEquipmentHandlerService.setSmartEquipmentState(fakeLamp, false, false);

        // Assert
        assertFalse(fakeLamp.isStateActive());
    }

    @Test
    public void shouldThrowCantActiveAlreadyActivatedEquipmentExceptionIfAlreadySmartEquipmentActivated() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        int fakeId = 1;

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp.setId(fakeId);
        if (!fakeLamp.isStateActive()) {
            fakeLamp.setStateActive(LocalDateTime.now(FIXED_CLOCK));
        }
    
        // Act
        Executable throwAct = () ->
                smartEquipmentHandlerService.setSmartEquipmentState(fakeLamp, true, false);
    
        // Assert
        assertThrows(CantActiveAlreadyActivatedStateException.class, throwAct);
    
    }

    @Test
    public void shouldThrowCantDeactivateAlreadyDeactivatedEquipmentExceptionIfAlreadySmartEquipmentDeactivated() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        int fakeId = 1;

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp.setId(fakeId);
        if (fakeLamp.isStateActive()) {
            fakeLamp.setStateInactive(LocalDateTime.now(FIXED_CLOCK));
        }

        // Act
        Executable throwAct = () ->
                smartEquipmentHandlerService.setSmartEquipmentState(fakeLamp, false, false);

        // Assert
        assertThrows(CantDeactivateAlreadyDeactivatedStateException.class, throwAct);

    }

    @Test
    public void shouldSetLastActivationTimeFromClockWhenTurnedOnTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK).minusDays(1));

        // Act
        smartEquipmentHandlerService.setSmartEquipmentState(fakeLamp, true, false);

        // Assert
        assertEquals(LocalDateTime.now(FIXED_CLOCK), fakeLamp.getLastActivationTime());
    }

    @Test
    public void shouldSetLastDeactivationTimeFromClockWhenTurnedOffTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK).minusDays(1));
        fakeLamp.setStateActive(LocalDateTime.now(FIXED_CLOCK).minusHours(1));

        // Act
        smartEquipmentHandlerService.setSmartEquipmentState(fakeLamp, false, false);

        // Assert
        assertEquals(LocalDateTime.now(FIXED_CLOCK), fakeLamp.getLastDeactivationTime());
    }

    @Test
    public void shouldTurnExtraStateOnWhenCameraAndExtraStateIsTrueTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Camera fakeCamera = new Camera("fakeCamera", LocalDateTime.now(FIXED_CLOCK));

        // Act
        String message = smartEquipmentHandlerService.setSmartEquipmentState(fakeCamera, true, true);

        // Assert
        assertEquals("lamp is on", message);
        assertEquals("lamp is on", fakeCamera.getExtraStateString());
    }

    @Test
    public void shouldTurnExtraStateOffWhenCameraAndExtraStateIsTrueTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Camera fakeCamera = new Camera("fakeCamera", LocalDateTime.now(FIXED_CLOCK));
        fakeCamera.setExtraStateOn();

        // Act
        String message = smartEquipmentHandlerService.setSmartEquipmentState(fakeCamera, false, true);

        // Assert
        assertEquals("lamp is off", message);
        assertEquals("lamp is off", fakeCamera.getExtraStateString());
    }

    @Test
    public void shouldNotChangeMainStateWhenExtraStateTurnedOnTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Camera fakeCamera = new Camera("fakeCamera", LocalDateTime.now(FIXED_CLOCK));

        // Act
        smartEquipmentHandlerService.setSmartEquipmentState(fakeCamera, true, true);

        // Assert
        assertFalse(fakeCamera.isStateActive());
    }

    @Test
    public void shouldNotChangeExtraStateWhenMainStateTurnedOnTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Camera fakeCamera = new Camera("fakeCamera", LocalDateTime.now(FIXED_CLOCK));

        // Act
        smartEquipmentHandlerService.setSmartEquipmentState(fakeCamera, true, false);

        // Assert
        assertTrue(fakeCamera.isStateActive());
        assertEquals("lamp is off", fakeCamera.getExtraStateString());
    }

    @Test
    public void shouldThrowSmartEquipmentHasNoExtraStateExceptionWhenLampAndExtraStateIsTrueTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));

        // Act
        Executable throwAct = () -> smartEquipmentHandlerService.setSmartEquipmentState(fakeLamp, true, true);

        // Assert
        assertThrows(SmartEquipmentHasNoExtraStateException.class, throwAct);
    }

    @Test
    public void shouldThrowCantActiveAlreadyActivatedStateExceptionWhenCameraExtraStateAlreadyOnTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Camera fakeCamera = new Camera("fakeCamera", LocalDateTime.now(FIXED_CLOCK));
        fakeCamera.setExtraStateOn();

        // Act
        Executable throwAct = () -> smartEquipmentHandlerService.setSmartEquipmentState(fakeCamera, true, true);

        // Assert
        assertThrows(CantActiveAlreadyActivatedStateException.class, throwAct);
    }

    @Test
    public void shouldReturnLampIsOffAsExtraStateStringWhenNewCameraTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Camera fakeCamera = new Camera("fakeCamera", LocalDateTime.now(FIXED_CLOCK));

        // Act
        String extraState = smartEquipmentHandlerService.getSmartEquipmentExtraStateString(fakeCamera);

        // Assert
        assertEquals("lamp is off", extraState);
    }

    @Test
    public void shouldReturnLampIsOnAsExtraStateStringWhenCameraLightOnTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Camera fakeCamera = new Camera("fakeCamera", LocalDateTime.now(FIXED_CLOCK));
        fakeCamera.setExtraStateOn();

        // Act
        String extraState = smartEquipmentHandlerService.getSmartEquipmentExtraStateString(fakeCamera);

        // Assert
        assertEquals("lamp is on", extraState);
    }

    @Test
    public void shouldReturnLampIsOffAsExtraStateStringWhenOnlyRecordingIsOnTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Camera fakeCamera = new Camera("fakeCamera", LocalDateTime.now(FIXED_CLOCK));
        fakeCamera.setStateActive(LocalDateTime.now(FIXED_CLOCK));

        // Act
        String extraState = smartEquipmentHandlerService.getSmartEquipmentExtraStateString(fakeCamera);

        // Assert
        assertEquals("lamp is off", extraState);
    }

    @Test
    public void shouldThrowSmartEquipmentHasNoExtraStateExceptionWhenGettingExtraStateOfLampTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        SmartEquipmentHandlerService smartEquipmentHandlerService = new SmartEquipmentHandlerService(smartEquipmentService, FIXED_CLOCK);
        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));

        // Act
        Executable throwAct = () -> smartEquipmentHandlerService.getSmartEquipmentExtraStateString(fakeLamp);

        // Assert
        assertThrows(SmartEquipmentHasNoExtraStateException.class, throwAct);
    }
}
