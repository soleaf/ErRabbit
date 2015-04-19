package org.mintcode.errabbit.core.report.dao;

import org.mintcode.errabbit.model.LogLevelDailyStatistics;
import org.mintcode.errabbit.model.Report;

/**
 * Created by soleaf on 4/6/15.
 */
public interface LogLevelDailyStatisticsRepositoryCustom {

    /**
     * Add Daily Statistic from report
     * @param report
     */
    public void insertDailyStatistic(Report report);

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
}
