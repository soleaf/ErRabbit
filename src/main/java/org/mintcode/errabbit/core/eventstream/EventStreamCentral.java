package org.mintcode.errabbit.core.eventstream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mintcode.errabbit.core.eventstream.event.EventChecker;
import org.mintcode.errabbit.core.eventstream.event.EventMapping;
import org.mintcode.errabbit.core.eventstream.event.EventMappingRepository;
import org.mintcode.errabbit.core.eventstream.stream.DefaultEventStream;
import org.mintcode.errabbit.core.eventstream.stream.EventStream;
import org.mintcode.errabbit.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * EventStreamCentral
 * Central has layers of event streams.
 * Take control event streams
 * Created by soleaf on 10/17/15.
 */
@Service
public class EventStreamCentral {

    private Logger logger = LogManager.getLogger();

    @Autowired
    private EventMappingRepository eventMappingRepository;

    @Autowired
    private ThreadPoolTaskExecutor jobExecutor;

    private EventStream eventStream;

    /**
     * Build event stream
     */
    @PostConstruct
    public void build(){
        logger.info("Building event stream...");
        // Get all event
        List<EventMapping> mappingList = eventMappingRepository.findAll();
        eventStream = new DefaultEventStream();
        eventStream.setJobExecutor(jobExecutor);
        for (EventMapping mapping : mappingList){
            eventStream.registerEventChecker(new EventChecker(mapping, eventStream));
        }
    }

    /**
     * Add log to stream
     * @param log
     */
    public void input(Log log){
        eventStream.input(log);
    }

}
