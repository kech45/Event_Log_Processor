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

    public Map<String, Long> eventCountPerUser() {
        return validEvents.stream().collect(Collectors.groupingBy(Event::getUserId, Collectors.counting()));
    }

    public Map<String, Double> purchaseStatistics() {
        double sum = 0;
        double max = 0;
        int purchaseCount = 0;
        double avg = 0;

        Map<String, Double> res = new HashMap<>();

        for (Event event : validEvents) {
            if (event.getAction().toUpperCase().equals(ActionType.PURCHASE.name())) {
                sum += event.getAmount();
                purchaseCount++;

                if (event.getAmount() > max) {
                    max = event.getAmount();
                }
            }
        }

        if(purchaseCount != 0) {
            avg = sum/purchaseCount;
        }
        res.put("Sum", sum);
        res.put("Max", max);
        res.put("Average", avg);

        return res;
    }

    public String mostActiveUser() {
        String mostActive = "";
        long max = 0;
        Map<String ,Long> userEventCountMap = eventCountPerUser();

        for(Map.Entry<String, Long> user : userEventCountMap.entrySet()) {
            if(user.getValue() > max) {
                mostActive = user.getKey();
                max = user.getValue();
            }
        }

        return mostActive;
    }

    public List<Map.Entry<String, Long>> mostActiveTop3Users() {
        Map<String ,Long> userEventCountMap = eventCountPerUser();
        List<Map.Entry<String, Long>> res = new ArrayList<>();

        for(Map.Entry<String, Long> user : userEventCountMap.entrySet()) {
            res.add(user);
        }

        //Sort descending
        Collections.sort(res, (a, b)
                -> Long.compare(b.getValue(), a.getValue()));

        //Excluding the element at 3rd index
        return res.subList(0, Math.min(3, res.size()));
    }

    public Map<String, Long> eventCountPerAction() {
        return validEvents.stream().collect(Collectors.groupingBy(Event::getAction, Collectors.counting()));
    }

    public List<Event> getValidEvents() {
        return validEvents;
    }
}