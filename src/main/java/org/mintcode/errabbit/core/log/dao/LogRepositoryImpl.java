package org.mintcode.errabbit.core.log.dao;

import com.mongodb.WriteResult;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.mintcode.errabbit.model.LogLevelHourStatistics;
import org.mintcode.errabbit.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.*;

/**
 * LogRepositoryImpl
 * Created by soleaf on 2015. 2. 19..
 */

public class LogRepositoryImpl implements LogRepositoryCustom {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MongoOperations mongoOperations;


    public WriteResult deleteRangeOfLoggingEventDateInt(String rabbitId, Integer begin, Integer end){
        Query query = new Query();
        query.addCriteria(Criteria.where("rabbitId").is(rabbitId)
                        .andOperator(Criteria.where("loggingEventDateInt").gte(begin),
                                Criteria.where("loggingEventDateInt").lte(end)
                        )
        );
        return mongoOperations.remove(query, Log.class);
    }

    public List<Log> findInRabbitId(Set<String> rabbitIdSet, Integer limit){
        Query query = new Query();
        query.addCriteria(Criteria.where("rabbitId").in(rabbitIdSet))
                .limit(limit);
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "_id")));
        return mongoOperations.find(query, Log.class);
    }

    @Override
    public void insertHourStatistic(Log log) {

        try {


            //COLLECTION_PREFIX + ".statistic"
        /*
            log.statstic
                {rabbit : 'rabbitID'
                ,year : year
                ,month : month
                ,day : day
                ,hour : hour
                ,level_error : n
                ,level_info : n
                ...}
         */

            // Extracting collectedDate
            // todo: Consider timezone of client
            Date date = new Date(log.getLoggingEvent().getTimeStamp());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Integer year = cal.get(Calendar.YEAR);
            Integer month = cal.get(Calendar.MONTH);
            Integer day = cal.get(Calendar.DAY_OF_MONTH);
            Integer hour = cal.get(Calendar.HOUR_OF_DAY);

            // Upsert + $inc
            Query query = new Query();
            query.addCriteria(Criteria.where("rabbitId").is(log.getRabbitId())
                    .andOperator(Criteria.where("year").is(year),
                            Criteria.where("month").is(month),
                            Criteria.where("day").is(day),
                            Criteria.where("hour").is(hour)
                    )
            );

            Update update = new Update().inc("level_" + log.getLoggingEvent().getLevel(), 1);
            mongoOperations.upsert(query, update, LogLevelHourStatistics.class);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }



}
