package org.mintcode.errabbit.core.rabbit.name;

import org.mintcode.errabbit.core.log.dao.LogLevelDailyStatisticsRepository;
import org.mintcode.errabbit.model.Log;
import org.mintcode.errabbit.model.LogLevelDailyStatistics;
import org.mintcode.errabbit.model.Rabbit;

import java.util.List;
import java.util.Map;

/**
 * Rabbit Name Repository
 * Speed access rabbit names
 * It use cache rabbit data for UI, because accessing frequently repository is very slow.
 * Created by soleaf on 2015. 2. 8..
 */
public interface RabbitCache {

    /**
     * Init or update rabbitID List
     * @param rabbits
     */
    public void updateRabbitIdList(Map<String,Rabbit> rabbits);

    /**
     * Check a rabbit id exists
     * @param id
     * @return
     */
    public boolean isRabbitId(String id);

    /**
     * Get Rabbit by id
     * @param id
     * @return
     */
    public Rabbit getRabbit(String id);

    /**
     * Get All rabbits as list
     * @return
     */
    public List<Rabbit> getRabbits();

    /**
     * Sync DailyStatistics
     */
    public void syncDailyStatistics();

    /**
     * Sync DailyStatistics only target rabbit
     * @param id
     */
    public void syncDailyStatistics(String id);

    /**
     * Update dailyStatistics
     * @param log
     */
    public void updateDailyStatistics(Log log);
    public void updateDailyStatistics(String rabbitId, String level, Integer loggingDateInt, Integer count);

    /**
     * Get DailyStatistics
     * @return
     */
    public Map<String, LogLevelDailyStatistics> getDailyStatisticsMap();
}
