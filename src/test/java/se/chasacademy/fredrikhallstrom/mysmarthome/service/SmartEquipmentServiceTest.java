package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchFoundNoSmartEquipmentsException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype.InMemoryRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SmartEquipmentServiceTest {
    @Test
    public void InsertsSmartEquipmentInInMemoryRepositoryTest() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
        SmartEquipment smartEquipment = mock(SmartEquipment.class);
        Clock clock = Clock.systemUTC();

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);

        // Act
        smartEquipmentService.addSmartEquipment(smartEquipment);

        // Assert
        verify(smartEquipmentRepository).insertSmartEquipment(smartEquipment);

    }

    @Test
    public void SetsIdInSmartEquipmentWhenInsertedIntoInMemoryRepositoryTest() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
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
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
        Clock clock = Clock.systemUTC();

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(clock));
        Integer fakeLampId = 1;

        Map<SmartEquipment, Integer> fakeEquipmentRepoMap = new HashMap<>();
        fakeEquipmentRepoMap.put(fakeLamp, fakeLampId);

        when(smartEquipmentRepository.getCurrentUniqueCounter()).thenReturn(fakeLampId);
        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(Map.of(fakeLamp, fakeLampId));

        doAnswer(testCommand -> {

            SmartEquipment removedSmartEquipment = testCommand.getArgument(0);
            fakeEquipmentRepoMap.remove(removedSmartEquipment);
            return null;
        }).when(smartEquipmentRepository).removeSmartEquipment(fakeLamp);

        // Act
        smartEquipmentService.removeSmartEquipment(fakeLamp);

        // Assert
        verify(smartEquipmentRepository).removeSmartEquipment(fakeLamp);
        assertFalse(fakeEquipmentRepoMap.containsKey(fakeLamp));

    }

    @Test
    public void shouldThrowRemoveOfSmartEquipmentDidFailSomewhereExceptionWhenRemoveFailed() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
        Clock clock = Clock.systemUTC();
        int fakeId = 1;
        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);

        Lamp nonExistentSmartEquipmentInRepo = new Lamp("notInRepo", LocalDateTime.now(clock));


        // Act
        Executable throwAct = () -> {
            smartEquipmentService.removeSmartEquipment(nonExistentSmartEquipmentInRepo);
        };

        // Assert
        assertThrows(RemoveOfSmartEquipmentDidFailSomewhereException.class, throwAct);

    }

    @Test
    public void shouldReturnListOfFoundEquipments() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
        Clock clock = Clock.systemUTC();
        int fakeIdStart = 1;

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(clock));
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(clock));
        Lamp fakeLamp3 = new Lamp("fakeLamp3", LocalDateTime.now(clock));

        Map<SmartEquipment, Integer> fakeRepoMap = new HashMap<>(Map.of(
                fakeLamp1, fakeIdStart,
                fakeLamp2, fakeIdStart + 1,
                fakeLamp3, fakeIdStart + 2)
        );

        List<SmartEquipment> referenceList = new ArrayList<>(fakeRepoMap.keySet());

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(fakeRepoMap);

        // Act
        List<SmartEquipment> foundSmartEquipmentList = smartEquipmentService.searchSmartEquipmentByName(
                "fake", SearchSortingTypes.NONE
        );

        // Assert
        assertEquals(referenceList, foundSmartEquipmentList);

    }

    @Test
    public void shouldThrowSearchFoundNoSmartEquipmentsExceptionIfNoSmartEquipmentIsFound() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
        Clock clock = Clock.systemUTC();
        int fakeIdStart = 1;

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(clock));
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(clock));
        Lamp fakeLamp3 = new Lamp("fakeLamp3", LocalDateTime.now(clock));

        Map<SmartEquipment, Integer> fakeRepoMap = new HashMap<>(Map.of(
                fakeLamp1, fakeIdStart,
                fakeLamp2, fakeIdStart + 1,
                fakeLamp3, fakeIdStart + 2)
        );

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(fakeRepoMap);

        // Act
        Executable throwAct = () -> {
            smartEquipmentService.searchSmartEquipmentByName("notInMap", SearchSortingTypes.NONE);
        };

        // Assert
        assertThrows(SearchFoundNoSmartEquipmentsException.class, throwAct);

    }

    @Test
    public void shouldSortSearchListInAscendingOrder() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
        Clock clock = Clock.systemUTC();
        int fakeIdStart = 1;

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(clock));
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(clock));
        Lamp fakeLamp3 = new Lamp("fakeLamp3", LocalDateTime.now(clock));

        Map<SmartEquipment, Integer> fakeRepoMap = new HashMap<>(Map.of(
                fakeLamp1, fakeIdStart,
                fakeLamp3, fakeIdStart + 2,
                fakeLamp2, fakeIdStart + 1)
        );

        List<SmartEquipment> referenceList = new ArrayList<>(List.of(
                fakeLamp1,
                fakeLamp2,
                fakeLamp3)
        );

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(fakeRepoMap);


        // Act
        List<SmartEquipment> foundSmartEquipmentSorted = smartEquipmentService.searchSmartEquipmentByName(
                "fake",
                SearchSortingTypes.ASCENDING);

        // Assert
        assertEquals(referenceList, foundSmartEquipmentSorted);
    }

    @Test
    public void shouldSortSearchListInDescendingOrder() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
        Clock clock = Clock.systemUTC();
        int fakeIdStart = 1;

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(clock));
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(clock));
        Lamp fakeLamp3 = new Lamp("fakeLamp3", LocalDateTime.now(clock));

        Map<SmartEquipment, Integer> fakeRepoMap = new HashMap<>(Map.of(
                fakeLamp1, fakeIdStart,
                fakeLamp3, fakeIdStart + 2,
                fakeLamp2, fakeIdStart + 1)
        );

        List<SmartEquipment> referenceList = new ArrayList<>(List.of(
                fakeLamp3,
                fakeLamp2,
                fakeLamp1)
        );

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(fakeRepoMap);


        // Act
        List<SmartEquipment> foundSmartEquipmentSorted = smartEquipmentService.searchSmartEquipmentByName(
                "fake",
                SearchSortingTypes.DESCENDING);

        // Assert
        assertEquals(referenceList, foundSmartEquipmentSorted);
    }



}
