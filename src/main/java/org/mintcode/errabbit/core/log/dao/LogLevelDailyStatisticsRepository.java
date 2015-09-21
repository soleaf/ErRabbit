package org.mintcode.errabbit.core.log.dao;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.model.LogLevelDailyStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * LogLevelDailyStatisticsRepository
 * Created by soleaf on 15. 4. 5..
 */
public interface LogLevelDailyStatisticsRepository
        extends MongoRepository<LogLevelDailyStatistics,ObjectId>, LogLevelDailyStatisticsRepositoryCustom {

    /**
     * Get by rabbitId, year, month
     * @param rabbitId
     * @param year
     * @param month
     * @return
     */
    public List<LogLevelDailyStatistics> findByRabbitIdAndYearAndMonth(String rabbitId,
                                                                       Integer year,
                                                                       Integer month);

    /**
     * Get by rabbitId
     * @param rabbitId
     * @return
     */
    public List<LogLevelDailyStatistics> deleteByRabbitId(String rabbitId);

}
