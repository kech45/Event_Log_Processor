package com.eventlog.validation;

import com.eventlog.model.ActionType;
import com.eventlog.model.Event;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventValidator {
    private int invalidEventCount;
    private List<Event> validEvents;

    private boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }

    private boolean isValidUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isValidTimestamp(String string) {
        try {
            Instant.parse(string);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidAction(String string) {
        try {
            ActionType.valueOf(string.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public List<Event> validate(List<Event> events) {
        validEvents = new ArrayList<>();
        invalidEventCount = 0;

        for (Event event : events) {
            if(isNullOrBlank(event.getTimestamp()) || isNullOrBlank(event.getEventId())
                    || isNullOrBlank(event.getUserId()) || isNullOrBlank(event.getAction())) {
                invalidEventCount++;
                continue;
            }

            if(!isValidUUID(event.getEventId()) || !isValidUUID(event.getUserId())) {
                invalidEventCount++;
                continue;
            }

            if(!isValidTimestamp(event.getTimestamp())) {
                invalidEventCount++;
                continue;
            }

            if(!isValidAction(event.getAction())) {
                invalidEventCount++;
                continue;
            }

            ActionType actionType = ActionType.valueOf(event.getAction().toUpperCase());

            if(actionType == ActionType.PURCHASE) {
                if(event.getAmount() == null || event.getAmount() <= 0) {
                    invalidEventCount++;
                    continue;
                }
            }

            if(actionType == ActionType.CLICK) {
                if(isNullOrBlank(event.getTarget())) {
                    invalidEventCount++;
                    continue;
                }
            }

            if(actionType == ActionType.VIEW) {
                if(isNullOrBlank(event.getArticleId())) {
                    invalidEventCount++;
                    continue;
                }
            }
            validEvents.add(event);
        }
        return validEvents;
    }

    public int getInvalidEventCount() {
        return invalidEventCount;
    }

    public List<Event> getValidEvents() {
        return validEvents;
    }
}
