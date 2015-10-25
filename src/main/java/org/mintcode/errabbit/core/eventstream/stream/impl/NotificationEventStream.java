package org.mintcode.errabbit.core.eventstream.stream.impl;

import org.mintcode.errabbit.core.eventstream.stream.AbstractEventStream;
import org.mintcode.errabbit.core.eventstream.stream.EventStreamPriority;

/**
 * NotificationEventStream
 * Events of System and NotificationCenter
 * Created by soleaf on 10/18/15.
 */
public class NotificationEventStream extends AbstractEventStream {

    @Override
    public EventStreamPriority getPriority() {
        return EventStreamPriority.high;
    }

}
