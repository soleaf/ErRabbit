package org.mintcode.errabbit.core.report.dao;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.model.LogLevelDailyStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by soleaf on 15. 4. 5..
 */
public interface LogLevelDailyStatisticsRepository
        extends MongoRepository<LogLevelDailyStatistics,ObjectId>, LogLevelDailyStatisticsRepositoryCustom {

    public List<LogLevelDailyStatistics> findByRabbitIdAndYearAndMonth(String rabbitId,
                                                                       Integer year,
                                                                       Integer month);

}
