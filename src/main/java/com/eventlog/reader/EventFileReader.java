package com.eventlog.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class EventFileReader {
    public List<String> readLines(String filePath) throws IOException {
        return Files.readAllLines(Path.of(filePath));
    }
}
