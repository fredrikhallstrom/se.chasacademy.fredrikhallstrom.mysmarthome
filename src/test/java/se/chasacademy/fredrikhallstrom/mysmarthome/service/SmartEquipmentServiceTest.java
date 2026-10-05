package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotypes.InMemoryRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SmartEquipmentServiceTest {
    @Test
    public void InsertsSmartEquipmentInInMemoryRepositoryTest() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = mock(InMemoryRepository.class);
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
        SmartEquipmentRepository smartEquipmentRepository = mock(InMemoryRepository.class);
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
        SmartEquipmentRepository smartEquipmentRepository = mock(InMemoryRepository.class);
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
        SmartEquipmentRepository smartEquipmentRepository = mock(InMemoryRepository.class);
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




}
