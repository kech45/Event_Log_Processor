package com.eventlog.validation;

import com.eventlog.model.Event;

public interface Validator {
    public boolean isValid(Event event);
}
