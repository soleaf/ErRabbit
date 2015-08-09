package org.mintcode.errabbit.core.log.dao;

import com.mongodb.WriteResult;
import org.mintcode.errabbit.model.Log;
import org.mintcode.errabbit.model.LogLevelDailyStatistics;

/**
 * Created by soleaf on 4/6/15.
 */
public interface LogLevelDailyStatisticsRepositoryCustom {

    /**
     * Add Daily Statistic from log
     * @param log
     */
    public void insertDailyStatistic(Log log);

    /**
     * Find First Statistic
     * @return
     */
    public LogLevelDailyStatistics findByRabbitIdOnFirst(String rabbitId);

    /**
     * Find Last Statistic
     * @return
     */
    public LogLevelDailyStatistics findByRabbitIdOnLast(String rabbitId);

    /**
     * Delete objects on range of days
     * @param rabbitId
     * @param begin
     * @param end
     */
    public WriteResult deleteDailyStatisticRangeOfLoggingEventDateInt(String rabbitId, Integer begin, Integer end);

}
