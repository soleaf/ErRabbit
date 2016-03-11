package org.mintcode.errabbit.core.collect.parser.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.mintcode.errabbit.core.collect.parser.LogParser;
import org.mintcode.errabbit.core.collect.parser.NotLoggingEventException;
import org.mintcode.errabbit.model.ErrLoggingEvent;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * PythonLogParser
 * This log parser use PythonLogDeserializer
 * Created by soleaf on 1/28/16.
 */
public class PythonLogParser implements LogParser {

    @Override
    public ErrLoggingEvent parseToLoggingEvent(Message msg) throws JMSException, NotLoggingEventException {

        ActiveMQBytesMessage bytesMessage = (ActiveMQBytesMessage) msg;
        String strMsg = new String(bytesMessage.getContent().data);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ErrLoggingEvent.class, new PythonLogDeserializer());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(strMsg, ErrLoggingEvent.class);

    }

}
