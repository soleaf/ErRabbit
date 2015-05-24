package org.mintcode.errabbit.core.report.dao;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.mintcode.errabbit.model.LogLevelDailyStatistics;
import org.mintcode.errabbit.model.LogLevelHourStatistics;
import org.mintcode.errabbit.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.*;

/**
 * Created by soleaf on 2015. 2. 19..
 */

public class ReportRepositoryImpl implements ReportRepositoryCustom {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void insertHourStatistic(Report report) {

        try {


            //COLLECTION_PREFIX + ".statistic"
        /*
            report.statstic
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
            Date date = new Date(report.getLoggingEvent().getTimeStamp());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Integer year = cal.get(Calendar.YEAR);
            Integer month = cal.get(Calendar.MONTH);
            Integer day = cal.get(Calendar.DAY_OF_MONTH);
            Integer hour = cal.get(Calendar.HOUR_OF_DAY);

            // Upsert + $inc
            Query query = new Query();
            query.addCriteria(Criteria.where("rabbitId").is(report.getRabbitId())
                    .andOperator(Criteria.where("year").is(year),
                            Criteria.where("month").is(month),
                            Criteria.where("day").is(day),
                            Criteria.where("hour").is(hour)
                    )
            );

            Update update = new Update().inc("level_" + report.getLoggingEvent().getLevel(), 1);
            mongoOperations.upsert(query, update, LogLevelHourStatistics.class);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }



}
