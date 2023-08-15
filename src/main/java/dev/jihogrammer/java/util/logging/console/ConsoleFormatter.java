package dev.jihogrammer.java.util.logging.console;

import dev.jihogrammer.java.util.logging.Event;
import dev.jihogrammer.java.util.logging.Formatter;

import java.time.format.DateTimeFormatter;

public final class ConsoleFormatter implements Formatter {
    private final String logFormat;
    private final DateTimeFormatter dateTimeFormatter;

    public ConsoleFormatter(String logFormat, DateTimeFormatter dateTimeFormatter) {
        this.logFormat = logFormat;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public String format(Event event) {
        return logFormat.formatted(
                        event.getTimestamp().format(dateTimeFormatter),
                        event.getThread().getName(),
                        event.getLevel(),
                        event.getName(),
                        body(event)
                );
    }

    private String body(Event event) {
        if (event.getFormat().contains("{}")) {
            StringBuilder sb = new StringBuilder();
            String[] tokens = event.getFormat().split("\\{}");

            int index = 0;
            while (index < tokens.length) {
                sb.append(tokens[index]);
                if (index < event.getArgs().length) {
                    sb.append(event.getArgs()[index].toString());
                }
                index++;
            }

            return sb.toString();
        }
        return event.getFormat();
    }
}
