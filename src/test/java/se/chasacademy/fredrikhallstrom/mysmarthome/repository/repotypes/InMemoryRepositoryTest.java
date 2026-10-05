package se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {
    @Test
    public void shouldReturnRepositoryMap() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = new InMemoryRepository();

        // Act
        Map<SmartEquipment, Integer> smartEquipmentMap = smartEquipmentRepository.getSmartEquipmentRepositoryMap();

        // Assert
        assertTrue(smartEquipmentMap.isEmpty());

    }

    @Test
    public void shouldReturnCorrectSmartEquipmentMap() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = new InMemoryRepository();

        Lamp lamp = new Lamp("Kök", LocalDateTime.now());
        int lampId = smartEquipmentRepository.getCurrentUniqueCounter();

        smartEquipmentRepository.insertSmartEquipment(lamp);


        // Act
        Map<SmartEquipment, Integer> smartEquipmentRepositoryTestMap = new HashMap<>();
        smartEquipmentRepositoryTestMap.put(lamp, lampId);

        // Assert
        assertEquals(smartEquipmentRepositoryTestMap, smartEquipmentRepository.getSmartEquipmentRepositoryMap());


    }

    @Test
    public void shouldIncreaseUniqueIdByOneWhenEquipmentInsertedTest() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = new InMemoryRepository();

        int idBefore = smartEquipmentRepository.getCurrentUniqueCounter();
        Lamp lamp = new Lamp("fakeLamp", LocalDateTime.now());

        // Act
        smartEquipmentRepository.insertSmartEquipment(lamp);
        int idAfter = smartEquipmentRepository.getCurrentUniqueCounter();

        // Assert
        assertEquals(1, idAfter - idBefore);

    }
}
