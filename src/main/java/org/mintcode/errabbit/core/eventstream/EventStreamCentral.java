package org.mintcode.errabbit.core.eventstream;

import org.mintcode.errabbit.core.eventstream.event.EventChecker;
import org.mintcode.errabbit.core.eventstream.event.EventMapping;
import org.mintcode.errabbit.core.eventstream.event.EventMappingRepository;
import org.mintcode.errabbit.core.eventstream.stream.DefaultEventStream;
import org.mintcode.errabbit.core.eventstream.stream.EventStream;
import org.mintcode.errabbit.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        try{
            if (eventStream == null){
                logger.info("Building event stream...");
            }
            else{
                logger.info("Rebuilding event stream..");
                eventStream.setActive(false);
            }
            eventStream = makeEventStream();
            eventStream.setActive(true);
            logger.info("Success building event stream.");
        }
        catch (Exception e){
            logger.error("Fail to build event stream reason : " + e.getMessage(),e);
        }

    }

    /**
     * Make eventStream from repository
     * @return
     */
    public EventStream makeEventStream(){
        // Get all event mappings
        List<EventMapping> mappingList = eventMappingRepository.findAll();
        EventStream es = new DefaultEventStream();
        es.setJobExecutor(jobExecutor);
        for (EventMapping mapping : mappingList){
            if (!mapping.getActive() || mapping.getActions() == null || mapping.getActions().isEmpty()){
                continue;
            }
            es.registerEventChecker(new EventChecker(mapping, es));
        }
        return es;
    }

    public EventStream getEventStream() {
        return eventStream;
    }

    public ThreadPoolTaskExecutor getJobExecutor() {
        return jobExecutor;
    }

    /**
     * Add log to stream
     * @param log
     */
    public void input(Log log){
        eventStream.input(log);
    }

}
