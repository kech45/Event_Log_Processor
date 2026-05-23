package com.eventlog.validation;

import com.eventlog.model.ActionType;
import com.eventlog.model.Event;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class EventValidator {
    private int invalidEventCount;
    private List<Event> validEvents;
    private final Validator baseValidator;
    private final Map<ActionType, Validator> strategies;

    public EventValidator() {
        this.baseValidator = new BaseFieldValidator();
        this.strategies = new EnumMap<>(ActionType.class);
        this.strategies.put(ActionType.PURCHASE, new PurchaseValidator());
        this.strategies.put(ActionType.CLICK, new ClickValidator());
        this.strategies.put(ActionType.VIEW, new ViewValidator());
    }

    public List<Event> validate(List<Event> events) {
        validEvents = new ArrayList<>();
        invalidEventCount = 0;

        for (Event event : events) {
            if(!baseValidator.isValid(event)){
                invalidEventCount++;
                continue;
            }

            ActionType actionType = ActionType.valueOf(event.getAction().toUpperCase());
            Validator specificValidator = strategies.get(actionType);

            if(specificValidator != null && specificValidator.isValid(event)) {
                this.validEvents.add(event);
            }
            else {
                invalidEventCount++;
            }
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
