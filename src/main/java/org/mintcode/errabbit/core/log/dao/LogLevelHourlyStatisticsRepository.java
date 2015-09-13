package org.mintcode.errabbit.core.log.dao;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.model.LogLevelHourStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by soleaf on 15. 9. 8..
 */
public interface LogLevelHourlyStatisticsRepository extends MongoRepository<LogLevelHourStatistics,ObjectId>,
        LogLevelHourlyStatisticsRepositoryCustom {

    public List<LogLevelHourStatistics> findByRabbitIdAndYearAndMonth(String rabbitId,
                                                                       Integer year,
                                                                       Integer month);

    public List<LogLevelHourStatistics> deleteByRabbitId(String rabbitId);

    public List<LogLevelHourStatistics> findByRabbitIdAndDateInt(String rabbitId, Integer dateInt);

}
