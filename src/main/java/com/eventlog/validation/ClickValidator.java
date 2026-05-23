package com.eventlog.validation;

import com.eventlog.model.Event;

public class ClickValidator implements Validator {
    @Override
    public boolean isValid(Event event) {
        return event.getTarget() != null && !event.getTarget().isBlank();
    }
}
