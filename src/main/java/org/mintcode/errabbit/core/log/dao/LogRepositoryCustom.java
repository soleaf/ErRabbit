package org.mintcode.errabbit.core.log.dao;

import com.mongodb.WriteResult;
import org.mintcode.errabbit.model.Log;

/**
 * Created by soleaf on 2015. 2. 19..
 */
public interface LogRepositoryCustom {

    /**
     * Add Hour Statistic from log
     * @param log
     */
    public void insertHourStatistic(Log log);

    /**
     * Remove logs on range of days
     * @param rabbitId
     * @param begin
     * @param end
     */
    public WriteResult deleteReportRangeOfLoggingEventDateInt(String rabbitId, Integer begin, Integer end);
}
