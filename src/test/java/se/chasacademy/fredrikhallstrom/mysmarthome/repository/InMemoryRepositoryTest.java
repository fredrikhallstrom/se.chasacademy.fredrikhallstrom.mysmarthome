package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotypes.InMemoryRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {
    private static InMemoryRepository inMemoryRepository;

    // Assures fresh RepositoryMap for tests
    @BeforeEach
    public void basicSetUpClass() {
        inMemoryRepository = new InMemoryRepository();
    }


    @Test
    public void shouldReturnRepositoryMap() {
        // Arrange

        // Act
        Map<SmartEquipment, Integer> smartEquipmentMap = inMemoryRepository.getSmartEquipmentRepositoryMap();

        // Assert
        assertTrue(smartEquipmentMap.isEmpty());

    }

    @Test
    public void shouldReturnCorrectSmartEquipmentMap() {
        // Arrange
        Lamp lamp = new Lamp("Kök", LocalDateTime.now());
        int lampId = inMemoryRepository.getCurrentUniqueCounter();

        inMemoryRepository.insertSmartEquipment(lamp);

        // Act
        inMemoryRepository.getSmartEquipment(lamp);
        Map<SmartEquipment, Integer> smartEquipmentRepositoryTestMap = new HashMap<>();
        smartEquipmentRepositoryTestMap.put(lamp, lampId);

        // Assert
        assertEquals(smartEquipmentRepositoryTestMap, inMemoryRepository.getSmartEquipmentRepositoryMap());


    }

    @Test
    public void shouldIncreaseUniqueIdByOneWhenEquipmentInsertedTest() {
        // Arrange
        int idBefore = inMemoryRepository.getCurrentUniqueCounter();
        Lamp lamp = new Lamp("fakeLamp", LocalDateTime.now());

        // Act
        inMemoryRepository.insertSmartEquipment(lamp);
        int idAfter = inMemoryRepository.getCurrentUniqueCounter();

        // Assert
        assertEquals(1, idAfter - idBefore);

    }
}
