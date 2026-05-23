package com.eventlog.validation;

import com.eventlog.model.ActionType;
import com.eventlog.model.Event;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.UUID;


public class BaseFieldValidator implements Validator {

    private boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }

    private boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isValidTimestamp(String timestamp) {
        try {
            Instant.parse(timestamp);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidAction(String action) {
        try {
            ActionType.valueOf(action.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean isValid(Event event) {
        if(isNullOrBlank(event.getTimestamp()) || isNullOrBlank(event.getEventId())
                || isNullOrBlank(event.getUserId()) || isNullOrBlank(event.getAction())) {
            return false;
        }

        if(!isValidUUID(event.getEventId()) || !isValidUUID(event.getUserId())) {
            return false;
        }

        if(!isValidTimestamp(event.getTimestamp())) {
            return false;
        }

        if(!isValidAction(event.getAction())) {
            return false;
        }

        return true;
    }
}
