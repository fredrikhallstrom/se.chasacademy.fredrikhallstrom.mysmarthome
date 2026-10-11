package se.chasacademy.fredrikhallstrom.mysmarthome.testutils;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public interface FixedClockForTest {
    Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2026-01-01T12:00:00Z"), ZoneOffset.UTC);

}
