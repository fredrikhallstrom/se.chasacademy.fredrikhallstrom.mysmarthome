package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.internal.util.Supplier;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentInRepositoryNotFoundException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.*;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SmartEquipmentServiceTest {
    @Test
    public void InsertsSmartEquipmentInInMemoryRepositoryTest() {
        // Arrange
        Clock clock = Clock.systemUTC();
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
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
        Integer fakeLampId = 1;

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(clock));
        fakeLamp.setId(fakeLampId);

        Map<SmartEquipment, Integer> fakeEquipmentRepoMap = new HashMap<>();
        fakeEquipmentRepoMap.put(fakeLamp, fakeLampId);

        when(smartEquipmentRepository.getCurrentUniqueCounter()).thenReturn(fakeLampId);
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
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
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
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
        Clock clock = Clock.systemUTC();

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository, clock);

        Map<SmartEquipment, Integer> fakeRepositoryMap = new HashMap<>();

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(fakeRepositoryMap);

        // Act
        Executable throwAct = smartEquipmentService::AllSmartEquipmentAsList;

        // Assert
        assertThrows(SmartEquipmentInRepositoryNotFoundException.class, throwAct);

    }



}
