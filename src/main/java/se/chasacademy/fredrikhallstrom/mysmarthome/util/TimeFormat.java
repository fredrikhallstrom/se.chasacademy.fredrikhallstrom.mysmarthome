package se.chasacademy.fredrikhallstrom.mysmarthome.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public interface TimeFormat {
        DateTimeFormatter DATE_TIME_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    default String formatLocalDateTimeAndTimeZoneForSystemDefault(LocalDateTime utcDateTime) {
        return utcDateTime.atZone(ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault())
                .format(DATE_TIME_FORMAT);
    }

}
