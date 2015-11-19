package org.mintcode.errabbit.core.log.dao;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.model.LogLevelHourStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * LogLevelHourlyStatisticsRepository
 * Created by soleaf on 15. 9. 8..
 */
public interface LogLevelHourlyStatisticsRepository extends MongoRepository<LogLevelHourStatistics,ObjectId>,
        LogLevelHourlyStatisticsRepositoryCustom {
    /**
     * Get by rabbitId
     * @param rabbitId
     * @return
     */
    public List<LogLevelHourStatistics> deleteByRabbitId(String rabbitId);

    /**
     * Get by rabbitid, datInt(ex:20150102)
     * @param rabbitId
     * @param dateInt
     * @return
     */
    public List<LogLevelHourStatistics> findByRabbitIdAndDateInt(String rabbitId, Integer dateInt);

}
