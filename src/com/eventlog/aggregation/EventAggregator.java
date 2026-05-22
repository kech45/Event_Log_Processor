package com.eventlog.aggregation;

import com.eventlog.model.ActionType;
import com.eventlog.model.Event;

import java.util.*;
import java.util.stream.Collectors;


public class EventAggregator {
    private List<Event> validEvents;

    public int totalValidEvents() {
        return validEvents.size();
    }
    public int totalInvalidLines(int invalidJson, int invalidEvent) {
        return invalidJson + invalidEvent;
    }

    public List<Event> getValidEvents() {
        return validEvents;
    }

    public Map<String, Long> eventCountPerUser() {
        return validEvents.stream().collect(Collectors.groupingBy(Event::getUserId, Collectors.counting()));
    }

    public List<Double> purchaseStatistics() {
        List<Double> res = new ArrayList<>();
        double sum = validEvents.stream()
                .filter(event -> event.getAction().toUpperCase().equals(ActionType.PURCHASE.name()))
                .mapToDouble(Event::getAmount).sum();

        res.add(sum);

        double average = validEvents.stream()
                .filter(event -> event.getAction().toUpperCase().equals(ActionType.PURCHASE.name()))
                .mapToDouble(Event::getAmount).average().orElse(0.0);

        res.add(average);

        double max = validEvents.stream()
                .filter(event -> event.getAction().toUpperCase().equals(ActionType.PURCHASE.name()))
                .mapToDouble(Event::getAmount).max().orElse(0.0);

        res.add(max);

        return res;
    }

}
