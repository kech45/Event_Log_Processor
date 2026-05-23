# Event Log Processor

A Java console application that reads an input.txt file with single-line JSON objects,
separated by newlines. Parses the valid JSON objects and handles invalid lines by keeping count.
Then those Events are validated against a set of rules, and at the end aggregated statistics are
produced from the valid events.


## Requirements

- Java 21
- Maven 3.8+


## Dependencies

- Junit 5.14.0
- Jackson 2.20.0


## How to Build and Run

### Build
```bash
mvn package
```

This compiles the source code, runs all tests, and produces a fat JAR in the `target/`
directory that includes all dependencies.
A `Fat Jar` is a single JAR file that contains the compiled application
code and also all of its dependencies, bundled together.


### Run
```
java -jar target/Event_Log_Processor-1.0-SNAPSHOT.jar
```

The application reads from `src/main/java/com/eventlog/resources/input.txt` by default.
Replace the file contents with your own event log to process different data.


### Run Tests Only
```
mvn test
```


## Project Structure

```
src/
   main/java/com/eventlog/
      aggregation/     # Statistics for all valid events
      model/           # Event, ActionType
      parser/          # JSON parsing
      reader/          # File reading
      resources/       # Input file
      validation/      # Validation logic and strategies
      Main.java        # Entry point
   test/java/com/eventlog/
      aggregation/
      parser/
      validation/
```


## How the Build Works (pom.xml)

- `maven-compiler-plugin` - compiles the source code targeting Java 21;
- `maven-surefire-plugin` - runs JUnit 5 tests when `mvn package` or `mvn test` are used;
- `maven-shade-plugin` - bundles the application and all its dependencies (Jackson) into a single executable
fat JAR. Without this, running the JAR would fail for Jackson classes 


## Design Decisions

### Strings over typed fields in Event
`eventId`, `userId`, and `timestamp` are stored as `String` rather than `UUID` and `Instant`.
If typed Java classes were used, Jackson would throw a deserialization exception on invalid values
before the validator could handle them gracefully. By keeping them as strings, invalid values can be caught
by the validator, then counted, and reported - which is what the spec requires.


### No polymorphism for Event subtypes
A single 'Event' class is used with nullable action-specific fields (`articleId`, `target`, `amount`),
paired with action types as Enumerable, rather than separate subclasses per action type.
This was done because, The Jackson deserialization would have added significant
complexity to the parsing layer. Abstraction is instead applied at the validation
layer through the Strategy pattern.


### Strategy pattern for validation
Action-specific validation (`PurchaseValidator`, `ClickValidator`, `ViewValidator`)
is handled through an `Validator` interface with separate implementations per action type,
registered in a `Map<ActionType, ActionValidator>`. This makes adding new event types
straightforward — a new validator class is added and registered, with no changes to
existing code (Open/Closed Principle). 


### Invalid line tracking
Invalid lines are tracked separately at two levels - the parser counts lines that fail JSON
deserialization, and the validator counts events that fail business rule validation.
Both counts can be summed during aggregation.


## Assumptions

- *Purchase amount must be greater than zero.* A purchase of zero has no point, so `amount <= 0`
is treated as invalid.
- *Blank lines are skipped* and not counted as invalid, as they are formatting artifacts
rather than malformed events.
- *Unknown actions are invalid.* Any action not defined in `ActionType` is rejected.
- *DefaultValidator is used both for Login and Logout* since there is no extra
functionality listed for them in the requirements.


## Tradeoffs

- Keeping `Event` as a single flat class with nullable fields is simpler for this scope, at the
cost of type safety for action-specific fields. A polymorphic model would be more fitting if
the number of event types or action-specific behaviors grew significantly.
- Invalid line counts are owned by the parser and validator separately rather than being centralized.
This is a deliberate separation of concerns, though it means the caller must sum them explicitly.


## Future Improvements

- **Docker support** - containerize the application for easy deployment and consistent runtime environments
- **Multithreading** - process file chunks in parallel using `parallelStream()`with thread-safe
collections improved performance on large files
