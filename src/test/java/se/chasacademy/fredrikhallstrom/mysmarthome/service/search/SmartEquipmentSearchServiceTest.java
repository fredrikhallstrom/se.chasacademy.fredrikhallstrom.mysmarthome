package se.chasacademy.fredrikhallstrom.mysmarthome.service.search;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchFoundNoSmartEquipmentsByNameException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmartEquipmentSearchServiceTest {
    @Test
    public void shouldReturnListOfFoundEquipments() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        Clock clock = Clock.systemUTC();

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", clock);
        Lamp fakeLamp2 = new Lamp("fakeLamp2", clock);
        Lamp fakeLamp3 = new Lamp("fakeLamp3", clock);

        List<SmartEquipment> fakeRepoList = new ArrayList<>(List.of(
                fakeLamp1,
                fakeLamp2,
                fakeLamp3)
        );

        List<SmartEquipment> referenceList = new ArrayList<>(List.copyOf(fakeRepoList));

        when(smartEquipmentService.listAllSmartEquipment()).thenReturn(fakeRepoList);

        // Act
        List<SmartEquipment> foundSmartEquipmentList = smartEquipmentSearchService.searchSmartEquipmentByName(
                "fake", SearchSortingType.NONE, smartEquipmentService
        );

        // Assert
        assertEquals(referenceList, foundSmartEquipmentList);

    }

    @Test
    public void shouldThrowSearchFoundNoSmartEquipmentsExceptionIfNoSmartEquipmentIsFound() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        Clock clock = Clock.systemUTC();

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", clock);
        Lamp fakeLamp2 = new Lamp("fakeLamp2", clock);
        Lamp fakeLamp3 = new Lamp("fakeLamp3", clock);

        List<SmartEquipment> fakeRepoList = new ArrayList<>(List.of(
                fakeLamp1,
                fakeLamp2,
                fakeLamp3)
        );

        when(smartEquipmentService.listAllSmartEquipment()).thenReturn(fakeRepoList);

        // Act
        Executable throwAct = () ->
                smartEquipmentSearchService.searchSmartEquipmentByName(
                        "notInMap", SearchSortingType.NONE, smartEquipmentService
                );


        // Assert
        assertThrows(SearchFoundNoSmartEquipmentsByNameException.class, throwAct);

    }

    @Test
    public void shouldSortSearchListInAscendingOrder() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        Clock clock = Clock.systemUTC();

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", clock);
        Lamp fakeLamp2 = new Lamp("fakeLamp2", clock);
        Lamp fakeLamp3 = new Lamp("fakeLamp3", clock);


        List<SmartEquipment> fakeRepoList = new ArrayList<>(List.of(
                fakeLamp1,
                fakeLamp2,
                fakeLamp3)
        );

        List<SmartEquipment> referenceList = new ArrayList<>(List.of(
                fakeLamp1,
                fakeLamp2,
                fakeLamp3)
        );

        when(smartEquipmentService.listAllSmartEquipment()).thenReturn(fakeRepoList);


        // Act
        List<SmartEquipment> foundSmartEquipmentSorted = smartEquipmentSearchService.searchSmartEquipmentByName(
                "fake",
                SearchSortingType.ASCENDING, smartEquipmentService);

        // Assert
        assertEquals(referenceList, foundSmartEquipmentSorted);
    }

    @Test
    public void shouldSortSearchListInDescendingOrder() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        Clock clock = Clock.systemUTC();

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", clock);
        Lamp fakeLamp2 = new Lamp("fakeLamp2", clock);
        Lamp fakeLamp3 = new Lamp("fakeLamp3", clock);

        List<SmartEquipment> fakeRepoList = new ArrayList<>(List.of(
                fakeLamp1,
                fakeLamp2,
                fakeLamp3)
        );

        List<SmartEquipment> referenceList = new ArrayList<>(List.of(
                fakeLamp3,
                fakeLamp2,
                fakeLamp1)
        );

        when(smartEquipmentService.listAllSmartEquipment()).thenReturn(fakeRepoList);


        // Act
        List<SmartEquipment> foundSmartEquipmentSorted = smartEquipmentSearchService.searchSmartEquipmentByName(
                "fake",
                SearchSortingType.DESCENDING, smartEquipmentService
        );

        // Assert
        assertEquals(referenceList, foundSmartEquipmentSorted);
    }

    @Test
    public void shouldReturnSmartEquipmentWhenCorrectIdInSearchId() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);
        Clock clock = Clock.systemUTC();

        int fakeIdStart = 1;

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", clock);
        fakeLamp1.setId(fakeIdStart);
        fakeIdStart++;

        Lamp fakeLamp2 = new Lamp("fakeLamp2", clock);
        fakeLamp2.setId(fakeIdStart);
        fakeIdStart++;

        Lamp fakeLamp3 = new Lamp("fakeLamp3", clock);
        fakeLamp3.setId(fakeIdStart);

        List<SmartEquipment> fakeRepoList = new ArrayList<>(List.of(
                fakeLamp1,
                fakeLamp2,
                fakeLamp3)
        );

        when(smartEquipmentService.listAllSmartEquipment()).thenReturn(fakeRepoList);

        // Act
        SmartEquipment gettedSmartEquipmentById = smartEquipmentSearchService.searchSmartEquipmentById(1);

        // Assert
        assertEquals(fakeLamp1, gettedSmartEquipmentById);

    }
}
