package org.mintcode.errabbit.core.rabbit.name;

import org.mintcode.errabbit.core.log.dao.LogLevelDailyStatisticsRepository;
import org.mintcode.errabbit.model.Log;
import org.mintcode.errabbit.model.LogLevelDailyStatistics;
import org.mintcode.errabbit.model.Rabbit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Rabbit and Rabbit group InMemory Cache
 * It use cache rabbit data for UI, because accessing frequently repository is very slow.
 * Created by soleaf on 2015. 2. 8..
 */
@Repository
public class InMemoryRabbitCache implements RabbitCache {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, Rabbit> rabbits;
    private Map<String, LogLevelDailyStatistics> dailyStatisticsMap;

    @Autowired
    LogLevelDailyStatisticsRepository logLevelDailyStatisticsRepository;

    public void updateRabbitIdList(Map<String, Rabbit> rabbits) {
        this.rabbits = rabbits;
        logger.trace("Rabbit Id List updated. size=" + rabbits.size());
    }

    public boolean isRabbitId(String id) {
        if (rabbits == null) {
            logger.error("Rabbit Id List null.");
            return false;
        }
        return rabbits.keySet().contains(id);
    }

    public Rabbit getRabbit(String id) {
        if (rabbits == null) {
            logger.error("Rabbit Id List null.");
            return null;
        }
        return rabbits.get(id);
    }

    public List<Rabbit> getRabbits() {
        return new ArrayList<>(rabbits.values());
    }

    public void syncDailyStatistics() {
        logger.info("syncDailyStatistics");
        dailyStatisticsMap = new HashMap<>();
        for (Rabbit rabbit : rabbits.values()) {
            syncDailyStatistics(rabbit.getId());
        }
    }

    public void syncDailyStatistics(String id) {
        LogLevelDailyStatistics statistics = logLevelDailyStatisticsRepository.findByRabbitIdOnLast(id);
        if (statistics != null) {
            dailyStatisticsMap.put(id, statistics);
        } else {
            dailyStatisticsMap.remove(id);
        }
    }

    public void updateDailyStatistics(String rabbitId, String level, Integer loggingDateInt, Integer count){
        LogLevelDailyStatistics statistics = dailyStatisticsMap.get(rabbitId);

        if (statistics == null || !statistics.getDateInt().equals(loggingDateInt)) {
            logger.info("statistics is null or mismatch date. sync daily statistics");
            syncDailyStatistics();
            statistics = dailyStatisticsMap.get(rabbitId);
        }
        if (!statistics.getDateInt().equals(loggingDateInt)) {
            logger.warn("Not found LogLevelDailyStatistics for " + rabbitId + " , " +loggingDateInt);
            return;
        }

        if (level.equals("TRACE"))
            statistics.setLevel_TRACE(statistics.getLevel_TRACE() + count);
        else if (level.equals("DEBUG"))
            statistics.setLevel_DEBUG(statistics.getLevel_DEBUG() + count);
        else if (level.equals("INFO"))
            statistics.setLevel_INFO(statistics.getLevel_INFO() + count);
        else if (level.equals("WARN"))
            statistics.setLevel_WARN(statistics.getLevel_WARN() + count);
        else if (level.equals("ERROR"))
            statistics.setLevel_ERROR(statistics.getLevel_ERROR() + count);
        else if (level.equals("FATAL"))
            statistics.setLevel_FATAL(statistics.getLevel_FATAL() + count);
    }

    public void updateDailyStatistics(Log log) {
        String rabbitId = log.getRabbitId();
        String level = log.getLoggingEvent().getLevel();
        updateDailyStatistics(rabbitId, level, log.getLoggingEventDateInt(), 1);
    }

    public Map<String, LogLevelDailyStatistics> getDailyStatisticsMap() {
        return dailyStatisticsMap;
    }
}
