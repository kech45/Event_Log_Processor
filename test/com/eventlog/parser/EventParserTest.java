package com.eventlog.parser;

import com.eventlog.model.Event;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class EventParserTest {
    @Test
    void invalidJSONReturnsInvalidEvent() {
        EventParser parser = new EventParser();
        List<String> lines = List.of("{Invalid}");

        List<Event> events = parser.parse(lines);

        assertEquals(0, events.size());
    }

    @Test
    void validJSONReturnsValidEvent() {
        EventParser parser = new EventParser();
        List<String> lines =
                List.of("{\"timestamp\":\"2026-05-01T10:00:00Z\", \"eventId\":\"550e8400-e29b-41d4-a716-446655440000\", \"userId\":\"c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b001\", \"action\":\"login\"}");

        List<Event> events = parser.parse(lines);

        assertEquals(1, events.size());

    }
}
