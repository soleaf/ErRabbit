package org.mintcode.errabbit.core.eventstream.stream;

import org.mintcode.errabbit.core.eventstream.event.EventChecker;
import org.mintcode.errabbit.core.eventstream.event.EventMapping;
import org.mintcode.errabbit.core.eventstream.event.action.EventAction;
import org.mintcode.errabbit.model.Log;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Set;

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

    public void setActive(Boolean active);

    public Boolean isActive();

    public void setJobExecutor(ThreadPoolTaskExecutor executor);

    public void runAction(EventMapping eventMapping, EventAction action, Log log);

    public Set<EventChecker> getEventCheckers();

    public String makeDescription();
}
