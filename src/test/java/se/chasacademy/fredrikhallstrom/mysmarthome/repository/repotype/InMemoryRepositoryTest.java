package se.chasacademy.fredrikhallstrom.mysmarthome.repository.repotype;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.InsertOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepositoryFollowContractTest;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepositoryInterface;
import se.chasacademy.fredrikhallstrom.mysmarthome.testutils.FixedClockForTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest extends SmartEquipmentRepositoryFollowContractTest implements FixedClockForTest {
    @Override
    protected SmartEquipmentRepositoryInterface createTestingRepository() {
        return new InMemoryRepository();
    }

    @Test
    public void shouldReturnRepositoryMap() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();

        // Act
        Map<SmartEquipment, Integer> smartEquipmentMap = smartEquipmentRepository.getSmartEquipmentRepositoryMap();

        // Assert
        assertTrue(smartEquipmentMap.isEmpty());

    }

    @Test
    public void shouldReturnRepositoryMapInReadOnly() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();
        Map<SmartEquipment, Integer> smartEquipmentIntegerMap = smartEquipmentRepository.getSmartEquipmentRepositoryMap();

        // Act
        Executable throwAct = smartEquipmentIntegerMap::clear;

        // Assert
        assertThrows(UnsupportedOperationException.class, throwAct);

    }


    @Test
    public void shouldReturnCorrectSmartEquipmentMap() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();

        Lamp lamp = new Lamp("Kök", LocalDateTime.now(FIXED_CLOCK));
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
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();

        Lamp lamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));
        Lamp lamp2 = new Lamp("fakeLamp2", LocalDateTime.now(FIXED_CLOCK));
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
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();

        // Act
        Executable throwAct = () -> smartEquipmentRepository.removeSmartEquipment(-1);

        // Assert
        assertThrows(RemoveOfSmartEquipmentDidFailSomewhereException.class, throwAct);

    }

    @Test
    public void shouldRemoveSmartEquipmentFromMapTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        lamp.setId(smartEquipmentRepository.insertSmartEquipment(lamp));

        // Act
        smartEquipmentRepository.removeSmartEquipment(lamp.getId());

        // Assert
        assertFalse(smartEquipmentRepository.getSmartEquipmentRepositoryMap().containsKey(lamp));
    }

    @Test
    public void shouldReturnTrueWhenSmartEquipmentRemovedTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        lamp.setId(smartEquipmentRepository.insertSmartEquipment(lamp));

        // Act
        boolean removed = smartEquipmentRepository.removeSmartEquipment(lamp.getId());

        // Assert
        assertTrue(removed);
    }

    @Test
    public void shouldBeEmptyAfterRemovingLastSmartEquipmentTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        lamp.setId(smartEquipmentRepository.insertSmartEquipment(lamp));

        // Act
        smartEquipmentRepository.removeSmartEquipment(lamp.getId());

        // Assert
        assertTrue(smartEquipmentRepository.getSmartEquipmentRepositoryMap().isEmpty());
    }

    @Test
    public void shouldKeepOtherSmartEquipmentWhenOneIsRemovedTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        Lamp lamp2 = new Lamp("Hallway", LocalDateTime.now(FIXED_CLOCK));
        lamp.setId(smartEquipmentRepository.insertSmartEquipment(lamp));
        lamp2.setId(smartEquipmentRepository.insertSmartEquipment(lamp2));

        // Act
        smartEquipmentRepository.removeSmartEquipment(lamp.getId());

        // Assert
        assertEquals(Map.of(lamp2, lamp2.getId()), smartEquipmentRepository.getSmartEquipmentRepositoryMap());
    }

    @Test
    public void shouldGiveFirstSmartEquipmentId1000Test() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));

        // Act
        int assignedId = smartEquipmentRepository.insertSmartEquipment(lamp);

        // Assert
        assertEquals(1000, assignedId);
    }

    @Test
    public void shouldGiveDifferentIdsToTwoInsertedSmartEquipmentTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        Lamp lamp2 = new Lamp("Hallway", LocalDateTime.now(FIXED_CLOCK));
        int firstId = smartEquipmentRepository.insertSmartEquipment(lamp);

        // Act
        int secondId = smartEquipmentRepository.insertSmartEquipment(lamp2);

        // Assert
        assertNotEquals(firstId, secondId);
    }

    @Test
    public void shouldNotUseUpIdWhenInsertOfNullFailsTest() {
        // Arrange
        SmartEquipmentRepositoryInterface smartEquipmentRepository = createTestingRepository();
        Lamp lamp = new Lamp("Kitchen", LocalDateTime.now(FIXED_CLOCK));
        assertThrows(InsertOfSmartEquipmentDidFailSomewhereException.class,
                () -> smartEquipmentRepository.insertSmartEquipment(null));

        // Act
        int assignedId = smartEquipmentRepository.insertSmartEquipment(lamp);

        // Assert
        assertEquals(1000, assignedId);
    }
}
