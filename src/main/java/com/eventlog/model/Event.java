package main.java.com.eventlog.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Event {
    private final Timestamp timestamp;
    private final UUID eventId;
    private final UUID userId;
    private final String action;

    public Event(Timestamp timestamp, UUID eventId, UUID userId, String action) {
        this.timestamp = timestamp;
        this.eventId = eventId;
        this.userId = userId;
        this.action = action;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public UUID getEventId() {
        return eventId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }
}
