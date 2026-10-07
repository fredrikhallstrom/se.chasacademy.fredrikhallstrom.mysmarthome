package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.internal.util.Supplier;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentInRepositoryNotFoundException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Camera;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Radiator;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

import java.time.Clock;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SmartEquipmentServiceTest {
    @Test
    public void InsertsSmartEquipmentInInMemoryRepositoryTest() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);
        SmartEquipment smartEquipment = mock(SmartEquipment.class);

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository);

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

        SmartEquipment fakeLamp = new Lamp("fakeLamp", clock);

        int fakeId = 1;

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository);
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

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository);

        Lamp fakeLamp = new Lamp("fakeLamp", clock);
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
        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository);

        Lamp nonExistentSmartEquipmentInRepo = new Lamp("notInRepo", clock);


        // Act
        Executable throwAct = () ->
                smartEquipmentService.removeSmartEquipment(nonExistentSmartEquipmentInRepo);

        // Assert
        assertThrows(RemoveOfSmartEquipmentDidFailSomewhereException.class, throwAct);

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

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository);

        Lamp fakeLamp = new Lamp("fakeLamp", clock);
        Radiator fakeRadiator = new Radiator("fakeRadiator", clock);
        Camera fakeCamera = new Camera("fakeCamera", clock);
        Lamp fakeLamp2 = new Lamp("fakeLamp2", clock);

        List<SmartEquipment> referenceList = List.of(fakeLamp, fakeRadiator, fakeCamera, fakeLamp2);

        Map<SmartEquipment, Integer> fakeRepositoryMap = new LinkedHashMap<>();
        for (SmartEquipment smartEquipment : referenceList) {
            fakeRepositoryMap.put(smartEquipment, getFakeId.get());
        }

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(fakeRepositoryMap);

        // Act
        List<SmartEquipment> smartEquipmentList = smartEquipmentService.listAllSmartEquipment();

        // Assert
        assertEquals(referenceList, smartEquipmentList);

    }

    @Test
    public void shouldThrowSmartEquipmentInRepositoryNotFoundExceptionWhenMapIsEmpty() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = mock(SmartEquipmentRepository.class);

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(smartEquipmentRepository);

        Map<SmartEquipment, Integer> fakeRepositoryMap = new HashMap<>();

        when(smartEquipmentRepository.getSmartEquipmentRepositoryMap()).thenReturn(fakeRepositoryMap);

        // Act
        Executable throwAct = smartEquipmentService::listAllSmartEquipment;

        // Assert
        assertThrows(SmartEquipmentInRepositoryNotFoundException.class, throwAct);

    }



}
