package org.mintcode.errabbit.core.log.dao;

import com.mongodb.WriteResult;
import org.mintcode.errabbit.model.Log;
import org.mintcode.errabbit.model.LogLevelDailyStatistics;

import java.util.List;
import java.util.Map;

/**
 * LogLevelHourlyStatisticsRepositoryCustom
 * Created by soleaf on 15. 9. 8..
 */
public interface LogLevelHourlyStatisticsRepositoryCustom {

    /**
     * Add Daily Statistic from log
     * @param log
     */
    public void insertStatistic(Log log);
    public void insertStatistic(Map<String,Object> staticSet);

    /**
     * Delete objects on range of days
     * @param rabbitId
     * @param begin
     * @param end
     */
    public WriteResult deleteDailyStatisticRangeOfLoggingEventDateInt(String rabbitId, Integer begin, Integer end);

}
