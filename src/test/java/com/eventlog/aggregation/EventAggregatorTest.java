package com.eventlog.aggregation;

import com.eventlog.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventAggregatorTest {
    private List<Event> validEvents;
    private EventAggregator aggregator;

    @BeforeEach
    void setUp() {
        validEvents = new ArrayList<>();

        validEvents.add(new Event("2026-05-01T10:00:00Z", "550e8400-e29b-41d4-a716-446655440000",
                "c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b001", "login", null, null, null));
        validEvents.add(new Event("2026-05-01T10:00:45Z", "550e8400-e29b-41d4-a716-446655440001",
                "c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b002", "purchase", null, null, 15.00));
        validEvents.add(new Event("2026-05-01T10:00:05Z", "550e8400-e29b-41d4-a716-446655440002",
                "c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b003", "view", "35-eof", null, null));
        validEvents.add(new Event("2026-05-01T10:00:05Z", "550e8400-e29b-41d4-a716-446655440004",
                "c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b003", "purchase", null, null, 20.00));
        validEvents.add(new Event("2026-05-01T10:00:10Z", "550e8400-e29b-41d4-a716-446655440005",
                "c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b004", "logout", null, null, null));

        aggregator = new EventAggregator(validEvents);

    }

    @Test
    void testTotalValidEvents() {
        int result = aggregator.totalValidEvents();
        assertEquals(5, result);
    }

    @Test
    void testEventCountPerUser() {
        Map<String, Long> result = aggregator.eventCountPerUser();

        long value1 = result.get("c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b003");
        long value2 = result.get("c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b001");

        assertEquals(2, value1);
        assertEquals(1, value2);
    }

    @Test
    void testPurchaseStatistics() {
        Map<String, Double> result = aggregator.purchaseStatistics();

        double sum = result.get("Sum");
        double max = result.get("Max");
        double avg = result.get("Average");

        assertEquals(35, sum);
        assertEquals(20, max);
        assertEquals(17.50, avg);
    }

    @Test
    void testMostActiveUser() {
        String result = aggregator.mostActiveUser();
        assertEquals("c1b7d8f0-1c3a-4d95-8d0d-6df3f1d5b003", result);
    }

    @Test
    void testTop3ActiveUsers() {
        List<Map.Entry<String, Long>> result = aggregator.mostActiveTop3Users();

        assertEquals(3, result.size());
        assertEquals(2, result.getFirst().getValue());
        assertEquals(1, result.getLast().getValue());
    }

    @Test
    void testEventCountPerAction() {
        Map<String, Long> result = aggregator.eventCountPerAction();

        long value1 = result.get("purchase");
        long value2 = result.get("login");

        assertEquals(2, value1);
        assertEquals(1, value2);
    }

}
