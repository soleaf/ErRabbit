package org.mintcode.errabbit.core.console;

import org.mintcode.errabbit.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by soleaf on 6/7/15.
 */
@Service
public class WebSocketMessagingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * Forward log to console page
     * @param log
     */
    public void sendReportToConsole(Log log){
        try{
            template.convertAndSend("/topic/console", log.toHTML(true));
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

}
