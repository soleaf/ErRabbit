package org.mintcode.errabbit.core.log.dao;

import com.mongodb.WriteResult;
import org.mintcode.errabbit.model.Log;

import java.util.List;
import java.util.Set;

/**
 * LogRepositoryCustom
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
    public WriteResult deleteRangeOfLoggingEventDateInt(String rabbitId, Integer begin, Integer end);

    /**
     * Find only rabbitId in rabbitIdSet
     * with limit
     * @param rabbitIdSet
     * @param limit
     * @return
     */
    public List<Log> findInRabbitId(Set<String> rabbitIdSet, Integer limit);
}
