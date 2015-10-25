package org.mintcode.errabbit.core.eventstream.stream.impl;

import org.mintcode.errabbit.core.eventstream.stream.AbstractEventStream;
import org.mintcode.errabbit.core.eventstream.stream.EventStreamPriority;

/**
 * Created by soleaf on 10/19/15.
 */
public class UserEventStream extends AbstractEventStream {
    @Override
    public EventStreamPriority getPriority() {
        return EventStreamPriority.midium;
    }
}
