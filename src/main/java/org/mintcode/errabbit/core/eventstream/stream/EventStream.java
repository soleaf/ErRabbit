package org.mintcode.errabbit.core.eventstream.stream;

import org.mintcode.errabbit.core.eventstream.event.EventChecker;
import org.mintcode.errabbit.model.Log;

/**
 * EventSteam is set of event checkers
 * Input log, check event and fire
 * Created by soleaf on 10/18/15.
 */
public interface EventStream {

    /**
     * Register event to stream
     * @param eventChecker
     */
    public void registerEventChecker(EventChecker eventChecker);

    /**
     * Remove event from stream
     * @param eventChecker
     */
    public void removeEventChecker(EventChecker eventChecker);

    /**
     * Run all checker
     * @param log
     */
    public void input(Log log);
}
