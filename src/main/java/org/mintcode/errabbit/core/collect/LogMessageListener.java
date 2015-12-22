package org.mintcode.errabbit.core.collect;

import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.mintcode.errabbit.core.console.WebSocketMessagingService;
import org.mintcode.errabbit.core.log.dao.LogLevelDailyStatisticsRepository;
import org.mintcode.errabbit.core.log.dao.LogLevelHourlyStatisticsRepository;
import org.mintcode.errabbit.core.log.dao.LogRepository;
import org.mintcode.errabbit.core.rabbit.dao.RabbitRepository;
import org.mintcode.errabbit.core.rabbit.name.RabbitCache;
import org.mintcode.errabbit.model.ErrLoggingEvent;
import org.mintcode.errabbit.model.Log;
import org.mintcode.errabbit.model.Rabbit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.*;

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
    private LogRepository logRepository;

    @Autowired
    private RabbitRepository rabbitRepository;

    @Autowired
    private RabbitCache rabbitCache;

    @Autowired
    private LogLevelDailyStatisticsRepository logLevelDailyStatisticsRepository;

    @Autowired
    private LogLevelHourlyStatisticsRepository logLevelHourlyStatisticsRepository;

    @Autowired
    private WebSocketMessagingService webSocketMessagingService;

    private Long lastCacheTime = null;
    private Integer cachingSize = 1000;
    private List<Log> caching = new ArrayList<>();

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
            ErrLoggingEvent errLoggingEvent = parseToLoggingEvent((ObjectMessage) message);

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

            caching.add(log);
            lastCacheTime = System.currentTimeMillis();
            if (caching.size() > cachingSize) {
                flushCache();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * Check cache's time
     * If cache's time is old, call flushCache()
     */
    @Scheduled(fixedDelay = 1000)
    private void checkCache(){
        if (lastCacheTime == null){
            return;
        }

        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - lastCacheTime;
        double elapsedSeconds = tDelta / 1000.0;
        if (elapsedSeconds > 1.0 && !caching.isEmpty()){
            logger.debug("flush log cache by timeout " + elapsedSeconds);
            flushCache();
        }
    }

    /**
     * Save all caching logs and related objects to DB
     */
    @PreDestroy
    synchronized protected void flushCache() {

        if (caching == null || caching.isEmpty()){
            return;
        }
        lastCacheTime = null;

        // Add to dao
        logRepository.save(caching);

        Map<String,Map<String,Object>> hours = new HashMap<>();
        for (Log log : caching) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(log.getLoggingEvent().getTimeStamp()));

            // Hour
            String key = log.getRabbitId()  +"/"  + log.getLoggingEventDateInt() + "/" + cal.get(Calendar.HOUR_OF_DAY);
            Map<String,Object> hour = null;
            if (hours.containsKey(key)){
                hour = hours.get(key);
            }
            else{
                hour = new HashMap<>();
                hour.put("rabbitId", log.getRabbitId());
                hour.put("dateInt", log.getLoggingEventDateInt());
                hour.put("year", cal.get(Calendar.YEAR));
                hour.put("month", cal.get(Calendar.MONTH)+1);
                hour.put("day", cal.get(Calendar.DAY_OF_MONTH));
                hour.put("hour", cal.get(Calendar.HOUR_OF_DAY));
                hours.put(key, hour);
            }
            String levelKey = "level_" + log.getLoggingEvent().getLevel();
            if (hour.containsKey(levelKey)){
                hour.put(levelKey, (Integer) hour.get(levelKey) + 1);
                hour.put(levelKey, (Integer) hour.get(levelKey) + 1);
            }
            else{
                hour.put(levelKey, 1);
            }

            webSocketMessagingService.sendReportToConsole(log);
        }

        for (Map<String,Object> hour : hours.values()){
            logLevelDailyStatisticsRepository.insertStatistic(hour);
            logLevelHourlyStatisticsRepository.insertStatistic(hour);
            for (String key : hour.keySet()){
                if (key.startsWith("level_")){
                    String level = key.replaceAll("level_","");
                    rabbitCache.updateDailyStatistics((String) hour.get("rabbitId"),
                            level,
                            (Integer) hour.get("dateInt"),
                            (Integer) hour.get(key));
                }
            }
        }

        caching.clear();
    }

    /**
     * Parse ObjectMessage to ErLoggingEvent
     *
     * @param msg
     * @return
     * @throws JMSException
     * @throws NotLoggingEventException
     */
    protected ErrLoggingEvent parseToLoggingEvent(ObjectMessage msg) throws JMSException, NotLoggingEventException {
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
     * obj is any Log4jLoggingEvent(Log4j 2) or LoggingEvent(log4j 1)
     */
    public class NotLoggingEventException extends Exception {
        public NotLoggingEventException(Object obj) {
            super(String.format("Couldn't get logging event from '%s'", obj.toString()));
        }
    }
}
