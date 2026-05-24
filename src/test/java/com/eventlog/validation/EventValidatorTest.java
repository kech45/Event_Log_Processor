package com.eventlog.validation;

import com.eventlog.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventValidatorTest {
    private EventValidator validator;
    private List<Event> events;

    @BeforeEach
    void setUp() {
        validator = new EventValidator();
        events = new ArrayList<>();
    }


    @Test
    void validEventPass() {
        events.add(new Event("2026-05-01T10:00:00Z", "550e8400-e29b-41d4-a716-446655440000",
                "c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b001", "login", null, null, null));

        List<Event> validEvents = validator.validate(events);

        assertEquals(1, validEvents.size());
    }

    @Test
    void nullRequiredFieldEvent() {
        events.add(new Event("2026-05-01T10:00:00Z", "550e8400-e29b-41d4-a716-446655440000",
                null, "login", null, null, null));

        List<Event> validEvents = validator.validate(events);

        assertEquals(0, validEvents.size());
    }

    @Test
    void invalidTimestampEvent() {
        events.add(new Event("3030", "550e8400-e29b-41d4-a716-446655440000",
                "c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b001", "login", null, null, null));

        List<Event> validEvents = validator.validate(events);

        assertEquals(0, validEvents.size());
    }

    @Test
    void invalidUUIDEvent() {
        events.add(new Event("2026-05-01T10:00:00Z", "550e8400-e29b-41d4-a716-446655440000",
                "INVALID", "login", null, null, null));

        List<Event> validEvents = validator.validate(events);

        assertEquals(0, validEvents.size());
    }

    @Test
    void invalidActionEvent() {
        events.add(new Event("2026-05-01T10:00:00Z", "550e8400-e29b-41d4-a716-446655440000",
                "c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b001", "play", null, null, null));

        List<Event> validEvents = validator.validate(events);

        assertEquals(0, validEvents.size());
    }

    @Test
    void invalidPurchaseAmount() {
        events.add(new Event("2026-05-01T10:00:00Z", "550e8400-e29b-41d4-a716-446655440000",
                "c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b001", "purchase", null, null, -150.00));

        List<Event> validEvents = validator.validate(events);

        assertEquals(0, validEvents.size());
    }

    @Test
    void invalidClickTarget() {
        events.add(new Event("2026-05-01T10:00:00Z", "550e8400-e29b-41d4-a716-446655440000",
                "c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b001", "click", null, null, 150.00));

        List<Event> validEvents = validator.validate(events);

        assertEquals(0, validEvents.size());
    }

    @Test
    void invalidViewArticleId() {
        events.add(new Event("2026-05-01T10:00:00Z", "550e8400-e29b-41d4-a716-446655440000",
                "c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b001", "view", null, "store", 150.00));

        List<Event> validEvents = validator.validate(events);

        assertEquals(0, validEvents.size());
    }
}
