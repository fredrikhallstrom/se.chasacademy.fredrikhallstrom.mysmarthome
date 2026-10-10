package se.chasacademy.fredrikhallstrom.mysmarthome.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TimeFormatTest {
    private final TimeFormat timeFormat = new TimeFormat() {};

    private String expectedInSystemZone(LocalDateTime utcDateTime) {
        return utcDateTime.atZone(ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void shouldFormatUtcTimeToSystemDefaultZoneTest() {
        // Arrange
        LocalDateTime utcDateTime = LocalDateTime.of(2026, 6, 15, 14, 30, 45);

        // Act
        String result = timeFormat.formatLocalDateTimeAndTimeZoneForSystemDefault(utcDateTime);

        // Assert
        assertEquals(expectedInSystemZone(utcDateTime), result);
    }

    @Test
    public void shouldFormatMidnightUtcTest() {
        // Arrange
        LocalDateTime utcDateTime = LocalDateTime.of(2026, 1, 1, 0, 0, 0);

        // Act
        String result = timeFormat.formatLocalDateTimeAndTimeZoneForSystemDefault(utcDateTime);

        // Assert
        assertEquals(expectedInSystemZone(utcDateTime), result);
    }

    @Test
    public void shouldFormatLastSecondOfYearUtcTest() {
        // Arrange
        LocalDateTime utcDateTime = LocalDateTime.of(2025, 12, 31, 23, 59, 59);

        // Act
        String result = timeFormat.formatLocalDateTimeAndTimeZoneForSystemDefault(utcDateTime);

        // Assert
        assertEquals(expectedInSystemZone(utcDateTime), result);
    }

}
