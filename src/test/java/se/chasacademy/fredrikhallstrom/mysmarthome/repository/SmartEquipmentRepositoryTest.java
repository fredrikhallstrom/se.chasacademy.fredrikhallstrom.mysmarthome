package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SmartEquipmentRepositoryTest {
    private static SmartEquipmentRepository smartEquipmentRepository;

    @BeforeAll
    public static void basicSetUpClass() {
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

}
