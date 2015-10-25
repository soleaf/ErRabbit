package org.mintcode.errabbit.core.eventstream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mintcode.errabbit.core.eventstream.stream.EventStream;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.TreeSet;

/**
 * EventStreamCentral
 * Central has multi event streams.
 * Take control event streams
 * Created by soleaf on 10/17/15.
 */
@Service
public class EventStreamCentral {

    private Logger logger = LogManager.getLogger();

    Set<EventStream> streams = new TreeSet<>();

    /**
     * Rebuild event stream
     */
    @PostConstruct
    public void rebuild(){

    }

}
