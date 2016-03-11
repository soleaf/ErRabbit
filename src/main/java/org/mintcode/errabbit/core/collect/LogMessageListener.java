package org.mintcode.errabbit.core.collect;

import org.mintcode.errabbit.core.collect.parser.impl.Log4jParser;
import org.mintcode.errabbit.core.collect.parser.LogParser;
import org.mintcode.errabbit.core.collect.parser.impl.PythonLogParser;
import org.mintcode.errabbit.core.rabbit.name.RabbitCache;
import org.mintcode.errabbit.model.ErrLoggingEvent;
import org.mintcode.errabbit.model.Log;
import org.mintcode.errabbit.model.LoggerType;
import org.mintcode.errabbit.model.Rabbit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Date;

/**
 * ActiveMQ Message Listener.
 * It will receive reports from ErRabbit Appender.
 * And forward to another messaging chains
 * Created by soleaf on 2015. 1. 31..
 */
@Component(value = "reportMessageListener")
public class LogMessageListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitCache rabbitCache;

    @Autowired
    private LogBuffer logBuffer;

    @PostConstruct
    public void onStartup() {
        logger.info("ActiveMQ listener ready");
    }

    public void onMessage(Message message) {

        try {
            // Extract message
            String rabbitID = extractRabbitIDFromMessage(message);
            Rabbit rabbit = rabbitCache.getRabbit(rabbitID);
            if (rabbit == null) {
                logger.error(String.format("Rabbit ID %s is invalid", rabbitID));
                return;
            }

            // Parsing
            LogParser parser = selectParser(rabbit);
            ErrLoggingEvent errLoggingEvent = parser.parseToLoggingEvent(message);

            // Check option : accept only
            if (rabbit.getCollectionOnlyException() != null &&
                    rabbit.getCollectionOnlyException() &&
                    errLoggingEvent.getThrowableInfo() == null) {
                // Ignore
                return;
            }

            // Make log
            Log log = new Log();
            log.setRabbitId(rabbitID);
            log.setLoggingEvent(errLoggingEvent);
            log.setCollectedDate(new Date());

            // Buffer
            logBuffer.addLog(log);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * @param message
     * @return
     * @throws JMSException
     */
    protected String extractRabbitIDFromMessage(Message message) throws JMSException {
        String queueName = message.getJMSDestination().toString();
        return queueName.replace("queue://errabbit.report.", "");
    }

    /**
     * Select LogParser by message
     * No just parsing by Log4j message
     * @param rabbit
     * @return
     */
    protected LogParser selectParser(Rabbit rabbit){
        if (rabbit.getLoggerType().equals(LoggerType.Log4j)){
            return new Log4jParser();
        }
        else if (rabbit.getLoggerType().equals(LoggerType.PythonLogger)){
            return new PythonLogParser();
        }
        else{
            return new Log4jParser();
        }
    }
}
