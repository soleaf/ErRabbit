package org.mintcode.errabbit.core.report.dao;

import org.apache.log4j.Logger;
import org.mintcode.errabbit.model.LogLevelDailyStatistics;
import org.mintcode.errabbit.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by soleaf on 15. 4. 5..
 */
public class LogLevelDailyStatisticsRepositoryImpl implements LogLevelDailyStatisticsRepositoryCustom {

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private MongoOperations mongoOperations;

    public void insertDailyStatistic(Report report) {

        try {


            //COLLECTION_PREFIX + ".statistic"
        /*
            report.statstic
                {rabbit : 'rabbitID'
                ,year : year
                ,month : month
                ,day : day
                ,level_error : n
                ,level_info : n
                ...}
         */

            // Extracting collectedDate
            // todo: Consider timezone of client
            Date date = new Date(report.getLoggingEvent().getTimeStamp());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Integer year = cal.get(Calendar.YEAR);
            Integer month = cal.get(Calendar.MONTH)+ 1; // normal day
            Integer day = cal.get(Calendar.DAY_OF_MONTH);

            // Upsert + $inc
            Query query = new Query();
            query.addCriteria(Criteria.where("rabbitId").is(report.getRabbitId())
                            .andOperator(
                                    Criteria.where("year").is(year),
                                    Criteria.where("month").is(month),
                                    Criteria.where("day").is(day)
                            )
            );

            Update update = new Update().inc("level_" + report.getLoggingEvent().getLevel(), 1);
            mongoOperations.upsert(query, update, LogLevelDailyStatistics.class);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    @Override
    public LogLevelDailyStatistics findByRabbitIdOnFirst(String rabbitId) {
        Query query = new Query();
        query.limit(1);
        query.addCriteria(Criteria.where("rabbitId").is(rabbitId));
        query.with(new Sort(org.springframework.data.domain.Sort.Direction.ASC, "_id"));

        List<LogLevelDailyStatistics> list = mongoOperations.find(query, LogLevelDailyStatistics.class);

        if (list != null && list.size() > 0 ){
            return mongoOperations.find(query, LogLevelDailyStatistics.class).get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public LogLevelDailyStatistics findByRabbitIdOnLast(String rabbitId) {
        Query query = new Query();
        query.limit(1);
        query.addCriteria(Criteria.where("rabbitId").is(rabbitId));
        query.with(new Sort(org.springframework.data.domain.Sort.Direction.DESC, "_id"));

        List<LogLevelDailyStatistics> list = mongoOperations.find(query, LogLevelDailyStatistics.class);

        if (list != null && list.size() > 0 ){
            return mongoOperations.find(query, LogLevelDailyStatistics.class).get(0);
        }
        else{
            return null;
        }
    }

}
