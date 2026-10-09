package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.internal.util.Supplier;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.InvalidSmartEquipmentNameException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentInRepositoryNotFoundException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.*;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepositoryInterface;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype.InMemoryRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SmartEquipmentServiceTest {
    static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2026-01-01T12:00:00Z"), ZoneOffset.UTC);

    private SmartEquipmentService createServiceWithFixedClock() {
        return new SmartEquipmentService(new InMemoryRepository(), FIXED_CLOCK);
    }

    @Test
    public void InsertsSmartEquipmentInInMemoryRepositoryTest() {
        // Arrange
        Clock clock = Clock.systemUTC();
        SmartEquipmentRepositoryInterface smartEquipmentRepository = mock(SmartEquipmentRepositoryInterface.class);
        SmartEquipment smartEquipment = mock(SmartEquipment.class);

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);

        // Act
        smartEquipmentService.addSmartEquipment(smartEquipment);

        // Assert
        verify(smartEquipmentRepository).insertSmartEquipment(smartEquipment);

    }

    @Test
    public void SetsIdInSmartEquipmentWhenInsertedIntoInMemoryRepositoryTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = mock(SmartEquipmentRepositoryInterface.class);
        Clock clock = Clock.systemUTC();

        SmartEquipment fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(clock));

        int fakeId = 1;

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);
        when(smartEquipmentRepository.insertSmartEquipment(fakeLamp)).thenReturn(fakeId);

        // Act
        smartEquipmentService.addSmartEquipment(fakeLamp);

        // Assert
        assertEquals(fakeId, fakeLamp.getId());


    }

    @Test
    public void shouldRemoveSmartEquipmentTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = mock(SmartEquipmentRepositoryInterface.class);
        Clock clock = Clock.systemUTC();

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);
        Integer fakeLampId = 1;

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(clock));
        fakeLamp.setId(fakeLampId);

        Map<SmartEquipment, Integer> fakeEquipmentRepoMap = new HashMap<>();
        fakeEquipmentRepoMap.put(fakeLamp, fakeLampId);

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(Map.of(fakeLamp, fakeLampId));

        doAnswer(_ -> {
            fakeEquipmentRepoMap.remove(fakeLamp);
            return true;
        }).when(smartEquipmentRepository).removeSmartEquipment(fakeLamp.getId());

        // Act
        smartEquipmentService.removeSmartEquipment(Integer.toString(fakeLamp.getId()));

        // Assert
        verify(smartEquipmentRepository).removeSmartEquipment(fakeLamp.getId());
        assertFalse(fakeEquipmentRepoMap.containsKey(fakeLamp));

    }





    @Test
    public void shouldReturnListOfAllSmartEquipment() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = mock(SmartEquipmentRepositoryInterface.class);
        Clock clock = Clock.systemUTC();
        final int[] fakeIdArray = {1};

        Supplier<Integer> getFakeId = () -> {
            int fakeStartIdReturn = fakeIdArray[0];
            fakeIdArray[0]++;
            return fakeStartIdReturn;
        };

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);
        
        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(clock));
        Radiator fakeRadiator = new Radiator("fakeRadiator", LocalDateTime.now(clock));
        Camera fakeCamera = new Camera("fakeCamera", LocalDateTime.now(clock));
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(clock));

        List<SmartEquipment> referenceList = List.of(fakeLamp, fakeRadiator, fakeCamera, fakeLamp2);

        Map<SmartEquipment, Integer> fakeRepositoryMap = new LinkedHashMap<>();
        for (SmartEquipment smartEquipment : referenceList) {
            fakeRepositoryMap.put(smartEquipment, getFakeId.get());
        }

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(fakeRepositoryMap);

        // Act
        List<SmartEquipment> smartEquipmentList = smartEquipmentService.AllSmartEquipmentAsList();

        // Assert
        assertEquals(referenceList, smartEquipmentList);

    }

    @Test
    public void shouldThrowSmartEquipmentInRepositoryNotFoundExceptionWhenMapIsEmpty() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = mock(SmartEquipmentRepositoryInterface.class);
        Clock clock = Clock.systemUTC();

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);

        Map<SmartEquipment, Integer> fakeRepositoryMap = new HashMap<>();

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(fakeRepositoryMap);

        // Act
        Executable throwAct = smartEquipmentService::AllSmartEquipmentAsList;

        // Assert
        assertThrows(SmartEquipmentInRepositoryNotFoundException.class, throwAct);

    }


    // AI generated Tests - BEGIN
    // ---------- createSmartEquipment: normal cases ----------

    @Test
    public void shouldCreateSmartEquipmentWithNameAndInstallationTimeFromClockTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();

        // Act
        SmartEquipment created = smartEquipmentService.createSmartEquipment("Kitchen lamp", SmartEquipmentType.LAMP);

        // Assert
        assertInstanceOf(Lamp.class, created);
        assertEquals("Kitchen lamp", created.getName());
        assertEquals(LocalDateTime.now(FIXED_CLOCK), created.getInstallationTime());
    }

    @ParameterizedTest
    @EnumSource(SmartEquipmentType.class)
    public void shouldCreateCorrectTypeAndStoreItInRepositoryTest(SmartEquipmentType type) {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();

        // Act
        SmartEquipment created = smartEquipmentService.createSmartEquipment("Hallway", type);

        // Assert
        assertEquals(type.getDisplayName(), created.getClass().getSimpleName());
        assertTrue(smartEquipmentService.AllSmartEquipmentAsList().contains(created));
    }

    // ---------- createSmartEquipment: boundary cases ----------

    @Test
    public void shouldAcceptSingleLetterNameTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();

        // Act
        SmartEquipment created = smartEquipmentService.createSmartEquipment("a", SmartEquipmentType.LAMP);

        // Assert
        assertEquals("a", created.getName());
    }

    @Test
    public void shouldAcceptNameThatOnlyDiffersByOneCharacterFromExistingTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();
        SmartEquipment first = smartEquipmentService.createSmartEquipment("Kitchen", SmartEquipmentType.LAMP);

        // Act
        SmartEquipment second = smartEquipmentService.createSmartEquipment("Kitchen2", SmartEquipmentType.LAMP);

        // Assert
        assertEquals("Kitchen2", second.getName());
        assertNotEquals(first.getId(), second.getId());
    }

    // ---------- createSmartEquipment: error-handling cases ----------

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "2lamp", "lamp!", "Kök"})
    public void shouldThrowInvalidSmartEquipmentNameExceptionWhenNameHasInvalidFormatTest(String invalidName) {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();

        // Act
        Executable throwAct = () -> smartEquipmentService.createSmartEquipment(invalidName, SmartEquipmentType.LAMP);

        // Assert
        assertThrows(InvalidSmartEquipmentNameException.class, throwAct);
        assertThrows(SmartEquipmentInRepositoryNotFoundException.class, smartEquipmentService::AllSmartEquipmentAsList);
    }

    @Test
    public void shouldThrowInvalidSmartEquipmentNameExceptionWhenNameAlreadyExistsIgnoringCaseTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();
        smartEquipmentService.createSmartEquipment("Kitchen", SmartEquipmentType.LAMP);

        // Act
        Executable throwAct = () -> smartEquipmentService.createSmartEquipment("KITCHEN", SmartEquipmentType.RADIATOR);

        // Assert
        assertThrows(InvalidSmartEquipmentNameException.class, throwAct);
        assertEquals(1, smartEquipmentService.AllSmartEquipmentAsList().size());
    }

    @ParameterizedTest
    @EnumSource(SmartEquipmentType.class)
    public void shouldThrowInvalidSmartEquipmentNameExceptionWhenNameEqualsTypeTest(SmartEquipmentType type) {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();
        String nameSameAsType = type.getDisplayName().toLowerCase();

        // Act
        Executable throwAct = () -> smartEquipmentService.createSmartEquipment(nameSameAsType, type);

        // Assert
        assertThrows(InvalidSmartEquipmentNameException.class, throwAct);
    }

    // AI generated Tests - STOP
}
