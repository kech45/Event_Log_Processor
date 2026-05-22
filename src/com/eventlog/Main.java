package com.eventlog;

import com.eventlog.parser.EventParser;
import com.eventlog.reader.EventFileReader;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

         EventFileReader reader = new EventFileReader();
         EventParser parser = new EventParser();

         try {
             List<String> lines = reader.readLines("src/main/java/com/eventlog/resources/input.txt");
             parser.parse(lines);
             System.out.println(parser.getInvalidJsonCount());

         } catch (IOException e) {
             System.out.println("Failed to read file!");
         }
    }
}
