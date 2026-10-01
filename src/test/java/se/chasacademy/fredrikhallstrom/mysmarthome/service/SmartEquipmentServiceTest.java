package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import org.junit.jupiter.api.Test;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotypes.InMemoryRepository;

import java.time.Clock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SmartEquipmentServiceTest {
    @Test
    public void InsertsEquipmentInInMemoryRepositoryTest() {
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

}
