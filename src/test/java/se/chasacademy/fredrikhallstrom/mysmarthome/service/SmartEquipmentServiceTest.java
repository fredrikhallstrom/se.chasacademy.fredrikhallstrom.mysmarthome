package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import org.junit.jupiter.api.Test;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotypes.InMemoryRepository;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SmartEquipmentServiceTest {
    @Test
    public void InsertsSmartEquipmentInInMemoryRepositoryTest() {
        // Arrange
        InMemoryRepository inMemoryRepository = mock(InMemoryRepository.class);
        SmartEquipment smartEquipment = mock(SmartEquipment.class);
        Clock clock = Clock.systemUTC();

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(inMemoryRepository, clock);

        // Act
        smartEquipmentService.addSmartEquipment(smartEquipment);

        // Assert
        verify(inMemoryRepository).insertSmartEquipment(smartEquipment);

    }

    @Test
    public void SetsIdInSmartEquipmentWhenInsertedIntoInMemoryRepositoryTest() {
        // Arrange
        InMemoryRepository inMemoryRepository = mock(InMemoryRepository.class);
        Clock clock = Clock.systemUTC();

        SmartEquipment fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(clock));

        int fakeId = 1;

        SmartEquipmentService smartEquipmentService = new SmartEquipmentService(inMemoryRepository, clock);
        when(inMemoryRepository.insertSmartEquipment(fakeLamp)).thenReturn(fakeId);

        // Act
        smartEquipmentService.addSmartEquipment(fakeLamp);

        // Assert
        assertEquals(fakeId, fakeLamp.getId());


    }


}
