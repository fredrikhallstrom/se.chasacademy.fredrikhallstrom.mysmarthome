package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SmartEquipmentRepositoryTest {
    private static SmartEquipmentRepository smartEquipmentRepository;

    // Assures fresh RepositoryMap for tests
    @BeforeEach
    public void basicSetUpClass() {
        smartEquipmentRepository = new SmartEquipmentRepository();
    }


    @Test
    public void shouldReturnRepositoryMap() {
        // Arrange

        // Act
        Map<SmartEquipment, Integer> smartEquipmentMap = smartEquipmentRepository.getSmartEquipmentRepositoryMap();

        // Assert
        assertTrue(smartEquipmentMap.isEmpty());

    }

    @Test
    public void shouldReturnCorrectSmartEquipmentMap() {
        // Arrange
        Lamp lamp = new Lamp("Kök", LocalDateTime.now());
        int lampId = lamp.getId();

        smartEquipmentRepository.insertSmartEquipment(lamp, lampId);

        // Act
        smartEquipmentRepository.getSmartEquipment(lamp);
        Map<SmartEquipment, Integer> smartEquipmentTested = new HashMap<>();
        smartEquipmentTested.put(lamp, lamp.getId());

        Map<SmartEquipment, Integer> copyOfSmartEquipmentMap = smartEquipmentRepository.getSmartEquipmentRepositoryMap();

        // Assert
        assertEquals(smartEquipmentTested, copyOfSmartEquipmentMap);


    }

}
