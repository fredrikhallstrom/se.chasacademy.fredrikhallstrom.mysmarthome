package se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepositoryInterface;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class InMemoryRepositoryTest {
    @Test
    public void shouldReturnRepositoryMap() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = new InMemoryRepository();

        // Act
        Map<SmartEquipment, Integer> smartEquipmentMap = smartEquipmentRepository.getSmartEquipmentRepositoryMap();

        // Assert
        assertTrue(smartEquipmentMap.isEmpty());

    }

    @Test
    public void shouldReturnRepositoryMapInReadOnly() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = new InMemoryRepository();
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
        SmartEquipmentRepositoryInterface smartEquipmentRepository = new InMemoryRepository();

        Lamp lamp = new Lamp("Kök", LocalDateTime.now(clock));
        lamp.setId(1000); //Initial ID
        smartEquipmentRepository.insertSmartEquipment(lamp);

        // Act
        Map<SmartEquipment, Integer> smartEquipmentRepositoryTestMap = new HashMap<>();
        smartEquipmentRepositoryTestMap.put(lamp, lamp.getId());

        // Assert
        assertEquals(smartEquipmentRepositoryTestMap, smartEquipmentRepository.getSmartEquipmentRepositoryMap());


    }

    @Test
    public void shouldIncreaseUniqueIdByOneWhenEquipmentInsertedTest() {
        // Arrange
        Clock clock = Clock.systemUTC();
        SmartEquipmentRepositoryInterface smartEquipmentRepository = new InMemoryRepository();

        Lamp lamp = new Lamp("fakeLamp", LocalDateTime.now(clock));
        Lamp lamp2 = new Lamp("fakeLamp2", LocalDateTime.now(clock));
        smartEquipmentRepository.insertSmartEquipment(lamp);
        int assignedID = smartEquipmentRepository.insertSmartEquipment(lamp);
        lamp.setId(assignedID);
        // Act
        int assignedID2 = smartEquipmentRepository.insertSmartEquipment(lamp2);
        lamp2.setId(assignedID2);

        // Assert
        assertEquals(1, lamp2.getId() - lamp.getId());

    }
    @Test
    public void shouldThrowRemoveOfSmartEquipmentDidFailSomewhereExceptionWhenRemoveFailed() {
        // Arrange
        Clock clock = Clock.systemUTC();

        SmartEquipmentRepositoryInterface smartEquipmentRepository = new InMemoryRepository();

        // Act
        Executable throwAct = () -> smartEquipmentRepository.removeSmartEquipment(-1);

        // Assert
        assertThrows(RemoveOfSmartEquipmentDidFailSomewhereException.class, throwAct);

    }
}
