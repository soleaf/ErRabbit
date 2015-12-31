package org.mintcode.errabbit.core.collect;

import org.mintcode.errabbit.core.console.WebSocketMessagingService;
import org.mintcode.errabbit.core.log.dao.LogLevelDailyStatisticsRepository;
import org.mintcode.errabbit.core.log.dao.LogLevelHourlyStatisticsRepository;
import org.mintcode.errabbit.core.log.dao.LogRepository;
import org.mintcode.errabbit.core.rabbit.name.RabbitCache;
import org.mintcode.errabbit.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.*;

/**
 * Created by soleaf on 2015. 12. 31..
 */
@Service
public class LogBuffer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitCache rabbitCache;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private LogLevelDailyStatisticsRepository logLevelDailyStatisticsRepository;

    @Autowired
    private LogLevelHourlyStatisticsRepository logLevelHourlyStatisticsRepository;

    @Autowired
    private WebSocketMessagingService webSocketMessagingService;

    private Long lastCacheTime = null;
    private Integer cachingSize = 1000;
    private List<Log> caching = new ArrayList<>();

    /**
     * Add log to buffer
     * @param log
     */
    public void addLog(Log log){
        caching.add(log);
        lastCacheTime = System.currentTimeMillis();
        if (caching.size() > cachingSize) {
            flushCache();
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
}
