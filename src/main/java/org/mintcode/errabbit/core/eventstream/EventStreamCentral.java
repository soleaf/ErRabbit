package org.mintcode.errabbit.core.eventstream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mintcode.errabbit.core.eventstream.stream.EventStream;
import org.mintcode.errabbit.model.Log;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * EventStreamCentral
 * Central has layers of event streams.
 * Take control event streams
 * Created by soleaf on 10/17/15.
 */
@Service
public class EventStreamCentral {

    private Logger logger = LogManager.getLogger();

    Set<EventStream> streamSet = new HashSet<EventStream>();

    /**
     * Build event stream
     */
    @PostConstruct
    public void build(){

    }

    public void input(Log log){
        for (EventStream stream : streamSet){
            stream.input(log);
        }
    }

}
