package org.mintcode.errabbit.core.collect.parser;

import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.mintcode.errabbit.model.ErrLoggingEvent;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * Log4jParser
 * Implement LogParser
 * Just parse log4j object messsage to ErLoggingEvent Object
 * Created by soleaf on 2015. 12. 31..
 */
public class Log4jParser implements LogParser {

    @Override
    public boolean isAbleToParse(ObjectMessage msg) {
        try{
            Object obj = msg.getObject();
            if (obj instanceof Log4jLogEvent) {
                return true;
            }
            else{
                return false;
            }
        } catch (JMSException e) {
            return false;
        }
    }

    /**
     * Parse ObjectMessage to ErLoggingEvent
     *
     * @param msg
     * @return
     * @throws JMSException
     * @throws NotLoggingEventException
     */
    public ErrLoggingEvent parseToLoggingEvent(ObjectMessage msg) throws JMSException, NotLoggingEventException {

        // Parse
        Object obj = msg.getObject();
        ErrLoggingEvent errLoggingEvent;

        // From log4j2 JMS appender
        if (obj instanceof Log4jLogEvent) {
            errLoggingEvent = ErrLoggingEvent.fromLog4jLogEvent((Log4jLogEvent) obj);
        }
        // From log4j1 custom ErRabbit JMS appender
        else if (obj instanceof ErrLoggingEvent) {
            errLoggingEvent = (ErrLoggingEvent) obj;
        } else {
            throw new NotLoggingEventException(obj);
        }
        return errLoggingEvent;
    }

}
