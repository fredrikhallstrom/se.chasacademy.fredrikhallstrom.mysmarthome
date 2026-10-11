package se.chasacademy.fredrikhallstrom.mysmarthome.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.InsertOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.RemoveOfSmartEquipmentDidFailSomewhereException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.testutils.FixedClockForTest;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class SmartEquipmentRepositoryFollowContractTest implements FixedClockForTest {
    protected abstract SmartEquipmentRepositoryInterface createTestingRepository();

    SmartEquipmentRepositoryInterface repository;
    @BeforeEach
    public void initRepo() {
        repository = createTestingRepository();
    }

    @Test
    void shouldThrowRemoveOfSmartEquipmentDidFailSomewhereExceptionWhenRemovingUnknownIdTest() {
        // Arrange
        // Std init

        // Act
        Executable throwAct = () -> repository.removeSmartEquipment(999);

        // Assert
        assertThrows(RemoveOfSmartEquipmentDidFailSomewhereException.class, throwAct);
    }

    @Test
    public void shouldThrowInsertOfSmartEquipmentDidFailSomewhereExceptionWhenInsertionFailTest() {
        // Arrange
        // Std init
        Lamp lamp = null;

        // Act
        Executable throwAct = () -> repository.insertSmartEquipment(lamp);


        // Assert
        assertThrows(InsertOfSmartEquipmentDidFailSomewhereException.class, throwAct);
    }

    @Test
    public void shouldReturnReadOnlyRepoWhenGetSmartEquipmentRepoTest() {
        // Arrange
        // Std init
        repository.insertSmartEquipment(new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK)));
        Map<SmartEquipment, Integer> repoCopyMap = repository.getSmartEquipmentRepositoryMap();

        // Act
        Executable throwAct = repoCopyMap::clear;

        // Assert
        assertThrows(UnsupportedOperationException.class, throwAct);

    }


}

