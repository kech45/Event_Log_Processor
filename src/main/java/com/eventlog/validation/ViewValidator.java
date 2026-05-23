package com.eventlog.validation;

import com.eventlog.model.Event;

public class ViewValidator implements Validator {
    @Override
    public boolean isValid(Event event ) {
        return event.getArticleId() != null && !event.getArticleId().isBlank();
    }
}
