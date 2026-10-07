package se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

import java.time.Clock;
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
    public void shouldReturnRepositoryMapInReadOnly() {
        // Arrange
        SmartEquipmentRepository smartEquipmentRepository = new InMemoryRepository();
        Map<SmartEquipment, Integer> smartEquipmentIntegerMap = smartEquipmentRepository.getSmartEquipmentRepositoryMap();

        // Act
        Executable throwAct = smartEquipmentIntegerMap::clear;

        // Assert
        assertThrows(UnsupportedOperationException.class, throwAct);

    }


    @Test
    public void shouldReturnCorrectSmartEquipmentMap() {
        // Arrange
        Clock clock = Clock.systemUTC();
        SmartEquipmentRepository smartEquipmentRepository = new InMemoryRepository();

        Lamp lamp = new Lamp("Kök", LocalDateTime.now(clock), clock);
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
        Clock clock = Clock.systemUTC();
        SmartEquipmentRepository smartEquipmentRepository = new InMemoryRepository();

        int idBefore = smartEquipmentRepository.getCurrentUniqueCounter();
        Lamp lamp = new Lamp("fakeLamp", LocalDateTime.now(clock), clock);

        // Act
        smartEquipmentRepository.insertSmartEquipment(lamp);
        int idAfter = smartEquipmentRepository.getCurrentUniqueCounter();

        // Assert
        assertEquals(1, idAfter - idBefore);

    }
}
