package org.mintcode.errabbit.core.collect.parser;


/**
 * obj is any Log4jLoggingEvent(Log4j 2) or LoggingEvent(log4j 1)
 */
public class NotLoggingEventException extends Exception {
    public NotLoggingEventException(Object obj) {
        super(String.format("Couldn't get logging event from '%s'", obj.toString()));
    }
}