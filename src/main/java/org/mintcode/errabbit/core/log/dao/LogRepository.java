package org.mintcode.errabbit.core.log.dao;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * LogRepository
 * Created by soleaf on 2014. 11. 8..
 */

public interface LogRepository extends MongoRepository<Log,ObjectId>, LogRepositoryCustom {

    /**
     * Get by rabbitId using Page
     * @param rabbitId
     * @param pageable
     * @return
     */
    public Page<Log> findByRabbitId(String rabbitId, Pageable pageable);

    /**
     * Get by rabbitId, dateInt(ex:20150202) using page
     * @param loggingEventDateInt
     * @param rabbitId
     * @param pageable
     * @return
     */
    public Page<Log> findByLoggingEventDateIntAndRabbitId(Integer loggingEventDateInt, String rabbitId ,Pageable pageable);

    /**
     * Get by rabbitId, dateInt(ex:20150202), level using page
     * @param loggingEventDateInt
     * @param rabbitId
     * @param level
     * @param pageable
     * @return
     */
    @Query(value = "{ 'loggingEventDateInt' : ?0, 'rabbitId' : ?1, 'loggingEvent.level' : ?2 }")
    public Page<Log> findByLoggingEventDateIntAndRabbitIdAndLevel(Integer loggingEventDateInt, String rabbitId, String level ,Pageable pageable);

    /**
     * Get by rabbitId, dateInt(ex:20150202), className using page
     * @param loggingEventDateInt
     * @param rabbitId
     * @param className
     * @param pageable
     * @return
     */
    @Query(value = "{ 'loggingEventDateInt' : ?0, 'rabbitId' : ?1, 'loggingEvent.categoryName' : ?2 }")
    public Page<Log> findByLoggingEventDateIntAndRabbitIdAndClassName(Integer loggingEventDateInt, String rabbitId,  String className ,Pageable pageable);

    /**
     * Get by rabbitId, dateInt(ex:20150202), level, classname using page
     * @param rabbitId
     * @param loggingEventDateInt
     * @param level
     * @param className
     * @param pageable
     * @return
     */
    @Query(value = "{ 'loggingEventDateInt' : ?0, 'rabbitId' : ?1, 'loggingEvent.level' : ?2, 'loggingEvent.categoryName' : ?3 }")
    public Page<Log> findByLoggingEventDateIntAndRabbitIdAndLevelAndClassName(Integer loggingEventDateInt,
                                                                              String rabbitId,
                                                                              String level,
                                                                              String className,
                                                                              Pageable pageable);

    /**
     * Delete log by rabbitId (clean)
     * @param rabbitId
     * @return
     */
    public List<Log> deleteByRabbitId(String rabbitId);

}
