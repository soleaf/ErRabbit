package org.mintcode.errabbit.core.collect.parser;

import org.mintcode.errabbit.model.ErrLoggingEvent;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

/**
 * LogParser
 * This interface made for parsing multiple log types.
 * Convert(parse) From ObjectMessage to ErLoggingEvent Object.
 * Created by soleaf on 2015. 12. 31..
 */
public interface LogParser {

    public ErrLoggingEvent parseToLoggingEvent(Message message) throws JMSException, NotLoggingEventException;
}
