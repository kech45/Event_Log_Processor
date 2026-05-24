package com.eventlog;

import com.eventlog.aggregation.EventAggregator;
import com.eventlog.model.Event;
import com.eventlog.parser.EventParser;
import com.eventlog.reader.EventFileReader;
import com.eventlog.validation.EventValidator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        EventFileReader reader = new EventFileReader();
        EventParser parser = new EventParser();
        EventValidator  validator = new EventValidator();

         try {
             if(args.length == 0) {
                 System.out.println("Enter file path!");
                 return;
             }

             String filePath = args[0];
             List<String> lines = reader.readLines(filePath);
             List<Event> parsed = parser.parse(lines);
             List<Event> validEvents = validator.validate(parsed);
             EventAggregator aggregator = new EventAggregator(validEvents);

             System.out.println("Total valid events: " + aggregator.totalValidEvents());
             System.out.println("Total invalid lines: " +
                     aggregator.totalInvalidLines(parser.getInvalidJsonCount(), validator.getInvalidEventCount()));

             System.out.println("\nEvent count per user:");
             aggregator.eventCountPerUser().forEach((k, v) -> System.out.println(k + ": " + v));

             System.out.println("\nPurchase statistics:");
             Map<String, Double> stats = aggregator.purchaseStatistics();
             System.out.printf("Total purchase amount: %.2f%n", stats.get("Sum"));
             System.out.printf("Average purchase amount: %.2f%n", stats.get("Average"));
             System.out.printf("Largest purchase: %.2f%n", stats.get("Max"));

             System.out.println("\nMost active user: " + aggregator.mostActiveUser());

             System.out.println("\nTop 3 most active users:");
             aggregator.mostActiveTop3Users()
                     .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue().toString()));

             System.out.println("\nEvent count per action:");
             aggregator.eventCountPerAction().forEach((k, v) -> System.out.println(k + ": " + v));

         } catch (IOException e) {
             System.out.println("Failure during pipeline! " + e.getMessage());
         }
    }
}
