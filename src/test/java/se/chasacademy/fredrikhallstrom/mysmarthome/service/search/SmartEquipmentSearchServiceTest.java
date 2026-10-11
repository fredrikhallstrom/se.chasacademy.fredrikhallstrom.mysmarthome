package se.chasacademy.fredrikhallstrom.mysmarthome.service.search;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchFoundNoSmartEquipmentByIdException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchFoundNoSmartEquipmentsByNameException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SearchSortingTypeCantBeNullException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.SmartEquipmentInRepositoryNotFoundException;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.Lamp;
import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.service.SmartEquipmentService;
import se.chasacademy.fredrikhallstrom.mysmarthome.testutils.FixedClockForTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmartEquipmentSearchServiceTest implements FixedClockForTest {
    @Test
    public void shouldReturnListOfFoundEquipments() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp3 = new Lamp("fakeLamp3", LocalDateTime.now(FIXED_CLOCK));

        List<SmartEquipment> fakeRepoList = new ArrayList<>(List.of(
                fakeLamp1,
                fakeLamp2,
                fakeLamp3)
        );

        List<SmartEquipment> referenceList = new ArrayList<>(List.copyOf(fakeRepoList));

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(fakeRepoList);

        // Act
        List<SmartEquipment> foundSmartEquipmentList = smartEquipmentSearchService.searchSmartEquipmentByName(
                "fake", SearchSortingType.NONE
        );

        // Assert
        assertEquals(referenceList, foundSmartEquipmentList);

    }

    @Test
    public void shouldThrowSearchFoundNoSmartEquipmentsExceptionIfNoSmartEquipmentIsFound() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp3 = new Lamp("fakeLamp3", LocalDateTime.now(FIXED_CLOCK));

        List<SmartEquipment> fakeRepoList = new ArrayList<>(List.of(
                fakeLamp1,
                fakeLamp2,
                fakeLamp3)
        );

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(fakeRepoList);

        // Act
        Executable throwAct = () ->
                smartEquipmentSearchService.searchSmartEquipmentByName(
                        "notInMap", SearchSortingType.NONE
                );


        // Assert
        assertThrows(SearchFoundNoSmartEquipmentsByNameException.class, throwAct);

    }

    @Test
    public void shouldSortSearchListInAscendingOrder() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp3 = new Lamp("fakeLamp3", LocalDateTime.now(FIXED_CLOCK));

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

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(fakeRepoList);

        // Act
        List<SmartEquipment> foundSmartEquipmentSorted = smartEquipmentSearchService.searchSmartEquipmentByName(
                "fake",
                SearchSortingType.ASCENDING);

        // Assert
        assertEquals(referenceList, foundSmartEquipmentSorted);
    }

    @Test
    public void shouldSortSearchListInDescendingOrder() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp3 = new Lamp("fakeLamp3", LocalDateTime.now(FIXED_CLOCK));

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

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(fakeRepoList);

        // Act
        List<SmartEquipment> foundSmartEquipmentSorted = smartEquipmentSearchService.searchSmartEquipmentByName(
                "fake",
                SearchSortingType.DESCENDING
        );

        // Assert
        assertEquals(referenceList, foundSmartEquipmentSorted);
    }

    @Test
    public void shouldReturnSmartEquipmentWhenCorrectIdInSearchId() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        int fakeIdStart = 1;

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp1.setId(fakeIdStart);
        fakeIdStart++;

        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp2.setId(fakeIdStart);
        fakeIdStart++;

        Lamp fakeLamp3 = new Lamp("fakeLamp3", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp3.setId(fakeIdStart);

        List<SmartEquipment> fakeRepoList = new ArrayList<>(List.of(
                fakeLamp1,
                fakeLamp2,
                fakeLamp3)
        );

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(fakeRepoList);

        // Act
        SmartEquipment smartEquipmentByIdFromSearchService = smartEquipmentSearchService.searchSmartEquipmentById(1);

        // Assert
        assertEquals(fakeLamp1, smartEquipmentByIdFromSearchService);

    }

    @Test
    public void shouldReturnSecondSmartEquipmentWhenSearchingItsIdTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp1.setId(1);
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp2.setId(2);

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(List.of(fakeLamp1, fakeLamp2));

        // Act
        SmartEquipment found = smartEquipmentSearchService.searchSmartEquipmentById(2);

        // Assert
        assertEquals(fakeLamp2, found);
    }

    @Test
    public void shouldReturnLastSmartEquipmentWhenSearchingLastIdTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp1.setId(1);
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp2.setId(2);
        Lamp fakeLamp3 = new Lamp("fakeLamp3", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp3.setId(3);

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(List.of(fakeLamp1, fakeLamp2, fakeLamp3));

        // Act
        SmartEquipment found = smartEquipmentSearchService.searchSmartEquipmentById(3);

        // Assert
        assertEquals(fakeLamp3, found);
    }

    @Test
    public void shouldReturnOnlySmartEquipmentWhenListHasOneElementTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp.setId(1);

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(List.of(fakeLamp));

        // Act
        SmartEquipment found = smartEquipmentSearchService.searchSmartEquipmentById(1);

        // Assert
        assertEquals(fakeLamp, found);
    }

    @Test
    public void shouldThrowSearchFoundNoSmartEquipmentByIdExceptionWhenIdIsUnknownTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));
        fakeLamp.setId(1);

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(List.of(fakeLamp));

        // Act
        Executable throwAct = () -> smartEquipmentSearchService.searchSmartEquipmentById(999);

        // Assert
        assertThrows(SearchFoundNoSmartEquipmentByIdException.class, throwAct);
    }

    @Test
    public void shouldThrowSmartEquipmentInRepositoryNotFoundExceptionWhenSearchingIdInEmptyRepositoryTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenThrow(new SmartEquipmentInRepositoryNotFoundException());

        // Act
        Executable throwAct = () -> smartEquipmentSearchService.searchSmartEquipmentById(1);

        // Assert
        assertThrows(SmartEquipmentInRepositoryNotFoundException.class, throwAct);
    }

    @Test
    public void shouldReturnAllSmartEquipmentWhenSearchTermIsEmptyTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp2 = new Lamp("otherLamp", LocalDateTime.now(FIXED_CLOCK));

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(List.of(fakeLamp1, fakeLamp2));

        // Act
        List<SmartEquipment> found = smartEquipmentSearchService.searchSmartEquipmentByName("", SearchSortingType.NONE);

        // Assert
        assertEquals(List.of(fakeLamp1, fakeLamp2), found);
    }

    @Test
    public void shouldThrowSearchFoundNoSmartEquipmentsByNameExceptionWhenOnlyCaseDiffersTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp = new Lamp("fakeLamp", LocalDateTime.now(FIXED_CLOCK));

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(List.of(fakeLamp));

        // Act
        Executable throwAct = () -> smartEquipmentSearchService.searchSmartEquipmentByName("FAKE", SearchSortingType.NONE);

        // Assert
        assertThrows(SearchFoundNoSmartEquipmentsByNameException.class, throwAct);
    }

    @Test
    public void shouldThrowSmartEquipmentInRepositoryNotFoundExceptionWhenSearchingNameInEmptyRepositoryTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenThrow(new SmartEquipmentInRepositoryNotFoundException());

        // Act
        Executable throwAct = () -> smartEquipmentSearchService.searchSmartEquipmentByName("fake", SearchSortingType.NONE);

        // Assert
        assertThrows(SmartEquipmentInRepositoryNotFoundException.class, throwAct);
    }

    @Test
    public void shouldKeepOriginalOrderWhenSortingTypeIsNoneTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp2 = new Lamp("fakeLamp2", LocalDateTime.now(FIXED_CLOCK));
        Lamp fakeLamp3 = new Lamp("fakeLamp3", LocalDateTime.now(FIXED_CLOCK));

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(List.of(fakeLamp3, fakeLamp1, fakeLamp2));

        // Act
        List<SmartEquipment> foundSmartEquipment = smartEquipmentSearchService.searchSmartEquipmentByName("fake", SearchSortingType.NONE);

        // Assert
        assertEquals(List.of(fakeLamp3, fakeLamp1, fakeLamp2), foundSmartEquipment);
    }

    @Test
    public void shouldThrowSearchSortingTypeCantBeNullExceptionWhenSortingTypeIsNullTest() {
        // Arrange
        SmartEquipmentService smartEquipmentService = mock(SmartEquipmentService.class);

        Lamp fakeLamp1 = new Lamp("fakeLamp1", LocalDateTime.now(FIXED_CLOCK));

        SmartEquipmentSearchService smartEquipmentSearchService = new SmartEquipmentSearchService(smartEquipmentService);

        when(smartEquipmentService.AllSmartEquipmentAsList()).thenReturn(List.of(fakeLamp1));

        // Act
        Executable throwAct = () -> smartEquipmentSearchService.searchSmartEquipmentByName("fake", null);

        // Assert
        assertThrows(SearchSortingTypeCantBeNullException.class, throwAct);
    }
}
