package com.eventlog.model;

public class Event {
    //Timestamps and UUIDs are used as Strings, because if Timestamp and UUID was used
    //the Jackson parser would not be able to count invalid UUIDs and Timestamps

    private final String timestamp;
    private final String eventId;
    private final String userId;
    private final String action;

    //Action specific fields, all nullable - amount as well, because it has a wrapper Double

    private final String articleId;
    private final String target;
    private final Double amount;

    public Event(String timestamp, String eventId, String userId, String action,
                 String articleId, String target, Double amount) {
        this.timestamp = timestamp;
        this.eventId = eventId;
        this.userId = userId;
        this.action = action;

        this.articleId = articleId;
        this.target = target;
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public String getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getTarget() {
        return target;
    }

    public Double getAmount() {
        return amount;
    }
}
