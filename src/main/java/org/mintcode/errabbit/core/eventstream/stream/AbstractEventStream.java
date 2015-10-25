package org.mintcode.errabbit.core.eventstream.stream;

import org.mintcode.errabbit.core.eventstream.event.EventChecker;
import org.mintcode.errabbit.model.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by soleaf on 10/18/15.
 */
public abstract class AbstractEventStream implements EventStream {

    protected Set<EventChecker> eventCheckers = new HashSet<>();

    @Override
    public int compareTo(Object o) {
        EventStream oStream = (EventStream) o;
        return getPriority().getValue() - oStream.getPriority().getValue();
    }

    @Override
    public void registerEventChecker(EventChecker eventChecker) {
        eventCheckers.add(eventChecker);
    }

    @Override
    public void removeEventChecker(EventChecker eventChecker) {
        eventCheckers.remove(eventChecker);
    }

    public void check(Log log) {
        for (EventChecker checker : eventCheckers) {
            checker.check(log);
        }
    }

}
