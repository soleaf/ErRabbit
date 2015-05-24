package org.mintcode.errabbit.core.collect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.mintcode.errabbit.core.rabbit.name.RabbitNameCache;
import org.mintcode.errabbit.core.report.dao.LogLevelDailyStatisticsRepository;
import org.mintcode.errabbit.core.report.dao.ReportRepository;
import org.mintcode.errabbit.model.ErrLoggingEvent;
import org.mintcode.errabbit.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Date;

/**
 * ActiveMQ Message Listener.
 * It will receive reports from ErRabbit Appender.
 * Created by soleaf on 2015. 1. 31..
 */
@Component(value = "reportMessageListener")
public class ReportMessageListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private RabbitNameCache nameRepository;

    @Autowired
    private LogLevelDailyStatisticsRepository logLevelDailyStatisticsRepository;

    @PostConstruct
    public void onStartup(){
        logger.info("ActiveMQ Listener ready");
    }

    public void onMessage(Message message) {

        try {
            // Extract message
            String rabbitID = extractRabbitIDFromMessage(message);
            // Verifying Message
            if (!verify(rabbitID)) {
                logger.error(String.format("Rabbit ID %s is invalid", rabbitID));
                return;
            }

            ObjectMessage msg = (ObjectMessage) message;
            Object obj = msg.getObject();
            ErrLoggingEvent errLoggingEvent;
            // From log4j2 JMS appender
            if (obj instanceof Log4jLogEvent){
                errLoggingEvent = ErrLoggingEvent.fromLog4jLogEvent((Log4jLogEvent) obj);
            }
            // From log4j1 custom ErRabbit JMS appender
            else if (obj instanceof ErrLoggingEvent){
                errLoggingEvent = (ErrLoggingEvent) obj;
            }
            else{
                throw new NotLoggingEventException(obj);
            }

            Report report = new Report();
            report.setRabbitId(rabbitID);
            report.setLoggingEvent(errLoggingEvent);
            report.setCollectedDate(new Date());
            logger.trace("Received new report " + report);

            // Add to dao
            reportRepository.save(report);

            // Add to statistic dao
            logLevelDailyStatisticsRepository.insertDailyStatistic(report);

        } catch (Exception e) {
            e.printStackTrace();
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
        return queueName.replace("queue://errabbit.report.", ""); // todo: Out with setting var
    }

    /**
     * Verify Report
     * 1. Valid Rabbit ID
     *
     * @param rabbitID
     * @return
     */
    public boolean verify(String rabbitID) {
        return nameRepository.isRabbitId(rabbitID);
    }

    /**
     * obj is any Log4jLoggingEvent(Log4j 2) or LoggingEvent(log4j 1)
     */
    public class NotLoggingEventException extends Exception{
        public NotLoggingEventException(Object obj){
            super(String.format("Couldn't get logging event from '%s'", obj.toString()));
        }
    }
}
