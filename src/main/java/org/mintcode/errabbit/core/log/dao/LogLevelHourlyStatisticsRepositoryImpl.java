package org.mintcode.errabbit.core.log.dao;

import com.mongodb.*;
import org.mintcode.errabbit.model.Log;
import org.mintcode.errabbit.model.LogLevelHourStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * LogLevelHourlyStatisticsRepositoryImpl
 * Created by soleaf on 15. 9. 8..
 */
public class LogLevelHourlyStatisticsRepositoryImpl implements LogLevelHourlyStatisticsRepositoryCustom {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MongoClient mongoClient;

    public void insertStatistic(Map<String,Object> staticSet) {
        try {


            //COLLECTION_PREFIX + ".statistic"
        /*
            log.statstic
                {rabbit : 'rabbitID'
                ,year : year
                ,month : month
                ,day : day
                ,level_error : n
                ,level_info : n
                ...}
         */

            // Extracting collectedDate
            Integer year = (Integer) staticSet.get("year");
            Integer month = (Integer) staticSet.get("month");
            Integer day = (Integer) staticSet.get("day");
            Integer hour = (Integer) staticSet.get("hour");
            Integer dateInt = (Integer) staticSet.get("dateInt");
            String rabbitId = (String) staticSet.get("rabbitId");

            DB db = mongoClient.getDB("errabbit");
            DBCollection coll = db.getCollection("logs.statistic.hour");

            DBObject q = new BasicDBObject();
            q.put("dateInt", dateInt);
            q.put("rabbitId", rabbitId);
            q.put("year", year);
            q.put("month", month);
            q.put("day", day);
            q.put("hour", hour);

            DBObject u = new BasicDBObject();
            for (Map.Entry<String, Object> entry : staticSet.entrySet()){
                if (entry.getKey().startsWith("level_")){
                    u.put("$inc", new BasicDBObject(entry.getKey(), entry.getValue()));
                }
            }
            coll.update(q, u, true, false);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }


    @Override
    public void insertStatistic(Log log) {

        try {
            //COLLECTION_PREFIX + ".statistic"
        /*
            log.statstic
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
            Date date = new Date(log.getLoggingEvent().getTimeStamp());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Integer year = cal.get(Calendar.YEAR);
            Integer month = cal.get(Calendar.MONTH) + 1; // normal day
            Integer day = cal.get(Calendar.DAY_OF_MONTH);
            Integer hour = cal.get(Calendar.HOUR_OF_DAY);

            DateFormat format = new SimpleDateFormat("yyyyMMdd");
            Integer dateInt = Integer.parseInt(format.format(date));

            DB db = mongoClient.getDB("errabbit");
            DBCollection coll = db.getCollection("logs.statistic.hour");

            DBObject q = new BasicDBObject();
            q.put("dateInt", dateInt);
            q.put("year", year);
            q.put("month", month);
            q.put("day", day);
            q.put("hour", hour);
            q.put("rabbitId", log.getRabbitId());

            DBObject u = new BasicDBObject();
            u.put("$inc", new BasicDBObject("level_" + log.getLoggingEvent().getLevel(), 1));
            coll.update(q, u, true, false);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    @Override
    public WriteResult deleteDailyStatisticRangeOfLoggingEventDateInt(String rabbitId, Integer begin, Integer end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("rabbitId").is(rabbitId)
                        .andOperator(
                                Criteria.where("dateInt").gte(begin),
                                Criteria.where("dateInt").lte(end)
                        )
        );
        return mongoOperations.remove(query, LogLevelHourStatistics.class);
    }
}
