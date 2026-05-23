package com.eventlog.validation;

import com.eventlog.model.Event;

public class DefaultValidator implements  Validator {
    @Override
    public boolean isValid(Event event) {
        return true;
    }
}
