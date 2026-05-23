package com.eventlog.validation;

import com.eventlog.model.Event;

public class PurchaseValidator implements Validator {
    @Override
    public boolean isValid(Event event ) {
        return (event.getAmount() != null && event.getAmount() > 0);
    }
}
