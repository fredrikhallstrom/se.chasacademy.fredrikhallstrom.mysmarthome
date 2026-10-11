package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.internal.util.Supplier;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CreationOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.InvalidSmartEquipmentNameException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.MustBeANumberException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchFoundNoSmartEquipmentByIdException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentIdCantBeNegativeException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentInRepositoryNotFoundException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.*;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepositoryInterface;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype.InMemoryRepository;
import se.chasacademy.fredrikhallstrom.mysmarthome.testutils.FixedClockForTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SmartEquipmentServiceTest implements FixedClockForTest {

    private SmartEquipmentService createServiceWithFixedClock() {
        return new SmartEquipmentService(new InMemoryRepository(), FIXED_CLOCK);
    }

    @Test
    public void InsertsSmartEquipmentInInMemoryRepositoryTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = mock(SmartEquipmentRepositoryInterface.class);
        SmartEquipment smartEquipment = mock(SmartEquipment.class);

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, FIXED_CLOCK);

        // Act
        smartEquipmentService.addSmartEquipment(smartEquipment);

        // Assert
        verify(smartEquipmentRepository).insertSmartEquipment(smartEquipment);

    }

    @Test
    public void SetsIdInSmartEquipmentWhenInsertedIntoInMemoryRepositoryTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = mock(SmartEquipmentRepositoryInterface.class);

        SmartEquipment fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));

        int fakeId = 1;

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, FIXED_CLOCK);

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

        Integer fakeLampId = 1;

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp.setId(fakeLampId);

        Map<SmartEquipment, Integer> fakeEquipmentRepoMap = new HashMap<>();
        fakeEquipmentRepoMap.put(fakeLamp, fakeLampId);

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, FIXED_CLOCK);

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

        final int[] fakeIdArray = {1};

        Supplier<Integer> getFakeId = () -> {
            int fakeStartIdReturn = fakeIdArray[0];
            fakeIdArray[0]++;
            return fakeStartIdReturn;
        };

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));
        Radiator fakeRadiator = new Radiator("fakeRadiator", LocalDateTime.now(FIXED_CLOCK));
        Camera fakeCamera = new Camera("fakeCamera", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(FIXED_CLOCK));

        List<SmartEquipment> referenceList = List.of(fakeLamp, fakeRadiator, fakeCamera, fakeLamp2);

        Map<SmartEquipment, Integer> fakeRepositoryMap = new LinkedHashMap<>();
        for (SmartEquipment smartEquipment : referenceList) {
            fakeRepositoryMap.put(smartEquipment, getFakeId.get());
        }

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, FIXED_CLOCK);

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

        Map<SmartEquipment, Integer> fakeRepositoryMap = new HashMap<>();

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, FIXED_CLOCK);

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(fakeRepositoryMap);

        // Act
        Executable throwAct = smartEquipmentService::AllSmartEquipmentAsList;

        // Assert
        assertThrows(SmartEquipmentInRepositoryNotFoundException.class, throwAct);

    }

    @Test
    public void shouldReturnSortedMapOfIdAndSmartEquipmentByIdTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = mock(SmartEquipmentRepositoryInterface.class);

        int fakeIdStart = 1;

        Lamp lamp = new Lamp("Lamp", LocalDateTime.now(FIXED_CLOCK));
        lamp.setId(fakeIdStart);

        Radiator radiator = new Radiator("Radiator", LocalDateTime.now(FIXED_CLOCK));
        radiator.setId(fakeIdStart + 2);

        Camera camera = new Camera("Camera", LocalDateTime.now(FIXED_CLOCK));
        camera.setId(fakeIdStart + 1);

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, FIXED_CLOCK);

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap())
                .thenReturn(Map.of(lamp, lamp.getId(), radiator, radiator.getId(), camera, camera.getId()));

        // Act
        SortedMap<Integer, SmartEquipment> mapFromMethod = smartEquipmentService.getAvailableSmartEquipmentsSortedMapById();

        // Assert
        assertEquals(Map.of( // Asserts content
                fakeIdStart, lamp,
                fakeIdStart + 2, radiator,
                fakeIdStart + 1, camera),
                mapFromMethod);

        assertEquals(List.of( // Asserts order
                        fakeIdStart,
                        fakeIdStart + 1,
                        fakeIdStart + 2),
                List.copyOf(mapFromMethod
                        .keySet()));
    }

    // AI generated Tests - NOT_EDITED_FOR_REFERENCE - BEGIN
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
        String nameSameAsType = type.getDisplayName().toLowerCase();

        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();

        // Act
        Executable throwAct = () -> smartEquipmentService.createSmartEquipment(nameSameAsType, type);

        // Assert
        assertThrows(InvalidSmartEquipmentNameException.class, throwAct);
    }

    @Test
    public void shouldAcceptIdZeroFromRepositoryTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = mock(SmartEquipmentRepositoryInterface.class);

        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, FIXED_CLOCK);

        when(smartEquipmentRepository.insertSmartEquipment(lamp)).thenReturn(0);

        // Act
        SmartEquipment added = smartEquipmentService.addSmartEquipment(lamp);

        // Assert
        assertEquals(0, added.getId());
    }

    @Test
    public void shouldThrowSmartEquipmentIdCantBeNegativeExceptionWhenRepositoryGivesNegativeIdTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = mock(SmartEquipmentRepositoryInterface.class);

        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, FIXED_CLOCK);

        when(smartEquipmentRepository.insertSmartEquipment(lamp)).thenReturn(-5);

        // Act
        Executable throwAct = () -> smartEquipmentService.addSmartEquipment(lamp);

        // Assert
        assertThrows(SmartEquipmentIdCantBeNegativeException.class, throwAct);
    }

    @Test
    public void shouldThrowCreationOfSmartEquipmentDidFailSomewhereExceptionWhenIdAlreadySetTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = mock(SmartEquipmentRepositoryInterface.class);

        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        lamp.setId(5);

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, FIXED_CLOCK);

        when(smartEquipmentRepository.insertSmartEquipment(lamp)).thenReturn(7);

        // Act
        Executable throwAct = () -> smartEquipmentService.addSmartEquipment(lamp);

        // Assert
        assertThrows(CreationOfSmartEquipmentDidFailSomewhereException.class, throwAct);
    }

    @Test
    public void shouldRemoveOnlyGivenSmartEquipmentTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();
        SmartEquipment kitchen = smartEquipmentService.createSmartEquipment("Kitchen", SmartEquipmentType.LAMP);
        SmartEquipment hallway = smartEquipmentService.createSmartEquipment("Hallway", SmartEquipmentType.LAMP);

        // Act
        boolean removed = smartEquipmentService.removeSmartEquipment(Integer.toString(hallway.getId()));

        // Assert
        assertTrue(removed);
        assertEquals(List.of(kitchen), smartEquipmentService.AllSmartEquipmentAsList());
    }

    @Test
    public void shouldThrowMustBeANumberExceptionWhenRemoveIdIsLettersTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();

        // Act
        Executable throwAct = () -> smartEquipmentService.removeSmartEquipment("abc");

        // Assert
        assertThrows(MustBeANumberException.class, throwAct);
    }

    @Test
    public void shouldThrowMustBeANumberExceptionWhenRemoveIdIsNullTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();

        // Act
        Executable throwAct = () -> smartEquipmentService.removeSmartEquipment(null);

        // Assert
        assertThrows(MustBeANumberException.class, throwAct);
    }

    @Test
    public void shouldThrowRemoveOfSmartEquipmentDidFailSomewhereExceptionWhenIdIsUnknownTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();
        smartEquipmentService.createSmartEquipment("Kitchen", SmartEquipmentType.LAMP);

        // Act
        Executable throwAct = () -> smartEquipmentService.removeSmartEquipment("999");

        // Assert
        assertThrows(RemoveOfSmartEquipmentDidFailSomewhereException.class, throwAct);
    }

    @Test
    public void shouldReturnAllCreatedSmartEquipmentTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();
        SmartEquipment kitchen = smartEquipmentService.createSmartEquipment("Kitchen", SmartEquipmentType.LAMP);
        SmartEquipment hallway = smartEquipmentService.createSmartEquipment("Hallway", SmartEquipmentType.RADIATOR);

        // Act
        List<SmartEquipment> smartEquipmentList = smartEquipmentService.AllSmartEquipmentAsList();

        // Assert
        assertEquals(2, smartEquipmentList.size());
        assertTrue(smartEquipmentList.containsAll(List.of(kitchen, hallway)));
    }

    @Test
    public void shouldReturnListWithOneElementWhenOneSmartEquipmentExistsTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();
        SmartEquipment kitchen = smartEquipmentService.createSmartEquipment("Kitchen", SmartEquipmentType.LAMP);

        // Act
        List<SmartEquipment> smartEquipmentList = smartEquipmentService.AllSmartEquipmentAsList();

        // Assert
        assertEquals(List.of(kitchen), smartEquipmentList);
    }

    @Test
    public void shouldReturnEmptyMapWhenRepositoryIsEmptyTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();

        // Act
        SortedMap<Integer, SmartEquipment> mapFromMethod = smartEquipmentService.getAvailableSmartEquipmentsSortedMapById();

        // Assert
        assertTrue(mapFromMethod.isEmpty());
    }

    @Test
    public void shouldReturnMapWithOneEntryWhenOneSmartEquipmentExistsTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();
        SmartEquipment kitchen = smartEquipmentService.createSmartEquipment("Kitchen", SmartEquipmentType.LAMP);

        // Act
        SortedMap<Integer, SmartEquipment> mapFromMethod = smartEquipmentService.getAvailableSmartEquipmentsSortedMapById();

        // Assert
        assertEquals(Map.of(kitchen.getId(), kitchen), mapFromMethod);
    }

    @Test
    public void shouldReturnSmartEquipmentWithGivenIdTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();
        smartEquipmentService.createSmartEquipment("Kitchen", SmartEquipmentType.LAMP);
        SmartEquipment hallway = smartEquipmentService.createSmartEquipment("Hallway", SmartEquipmentType.RADIATOR);
        smartEquipmentService.createSmartEquipment("Garage", SmartEquipmentType.CAMERA);

        // Act
        SmartEquipment found = smartEquipmentService.getSmartEquipmentById(hallway.getId());

        // Assert
        assertEquals(hallway, found);
    }

    @Test
    public void shouldThrowSearchFoundNoSmartEquipmentByIdExceptionWhenIdIsUnknownTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();
        smartEquipmentService.createSmartEquipment("Kitchen", SmartEquipmentType.LAMP);

        // Act
        Executable throwAct = () -> smartEquipmentService.getSmartEquipmentById(999);

        // Assert
        assertThrows(SearchFoundNoSmartEquipmentByIdException.class, throwAct);
    }

    @Test
    public void shouldThrowSearchFoundNoSmartEquipmentByIdExceptionWhenRepositoryIsEmptyTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = createServiceWithFixedClock();

        // Act
        Executable throwAct = () -> smartEquipmentService.getSmartEquipmentById(1000);

        // Assert
        assertThrows(SearchFoundNoSmartEquipmentByIdException.class, throwAct);
    }

    // AI generated Tests - STOP
}
