package com.eventlog.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.eventlog.model.Event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventParser {
    private ObjectMapper mapper;
    private int invalidJsonCount;
    private List<Event> events;
    private Set<String> seenEvents;

    public EventParser() {
        mapper = new ObjectMapper();
    }

    public List<Event> parse(List<String> readStringList) {
        invalidJsonCount = 0;
        events = new ArrayList<>();
        seenEvents = new HashSet<>();
            for (String line : readStringList) {
                if(line.isBlank()) {
                    continue;
                }
                try {
                    Event toBeAdded = mapper.readValue(line, Event.class);
                    if(seenEvents.contains(toBeAdded.getEventId())) {
                        continue;
                    }
                    events.add(toBeAdded);
                    seenEvents.add(toBeAdded.getEventId());
                }  catch (JsonProcessingException e) {
                    invalidJsonCount++;
                }

        }
        return events;
    }

    public int getInvalidJsonCount() {
        return invalidJsonCount;
    }

    public List<Event> getEvents() {
        return events;
    }
}
