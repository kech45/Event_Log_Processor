package main.java.com.eventlog.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.eventlog.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventParser {
    private ObjectMapper mapper;
    private int invalidJsonCount;
    private List<Event> events;

    public EventParser() {
        mapper = new ObjectMapper();
    }

    public List<Event> parse(List<String> readStringList) {
        invalidJsonCount = 0;
        events = new ArrayList<>();
            for (String line : readStringList) {
                if(line.isBlank()) {
                    continue;
                }
                try {
                    events.add(mapper.readValue(line, Event.class));
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
