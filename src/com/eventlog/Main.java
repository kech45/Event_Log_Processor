package com.eventlog;

import com.eventlog.aggregation.EventAggregator;
import com.eventlog.model.Event;
import com.eventlog.parser.EventParser;
import com.eventlog.reader.EventFileReader;
import com.eventlog.validation.EventValidator;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EventFileReader reader = new EventFileReader();
        EventParser parser = new EventParser();
        EventValidator  validator = new EventValidator();

         try {
             List<String> lines = reader.readLines("src/com/eventlog/resources/input.txt");
             List<Event> parsed = parser.parse(lines);
             List<Event> validEvents = validator.validate(parsed);
             EventAggregator aggregator = new EventAggregator(validEvents);

             System.out.println(aggregator.eventCountPerUser());
             System.out.println(aggregator.totalValidEvents());
             System.out.println(aggregator.totalInvalidLines(parser.getInvalidJsonCount(), validator.getInvalidEventCount()));
             System.out.println(aggregator.purchaseStatistics());
             System.out.println(aggregator.eventCountPerAction());
             System.out.println(aggregator.mostActiveTop3Users());

         } catch (IOException e) {
             System.out.println("Failed to read file!");
         }
    }
}
