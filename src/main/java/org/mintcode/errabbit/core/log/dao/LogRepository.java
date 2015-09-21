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
     * @param rabbitId
     * @param loggingEventDateInt
     * @param pageable
     * @return
     */
    public Page<Log> findByRabbitIdAndLoggingEventDateInt(String rabbitId, Integer loggingEventDateInt ,Pageable pageable);

    /**
     * Get by rabbitId, dateInt(ex:20150202), level using page
     * @param rabbitId
     * @param loggingEventDateInt
     * @param level
     * @param pageable
     * @return
     */
    @Query(value = "{ 'rabbitId' : ?0, 'loggingEventDateInt' : ?1, 'loggingEvent.level' : ?2 }")
    public Page<Log> findByRabbitIdAndLoggingEventDateIntAndLevel(String rabbitId, Integer loggingEventDateInt, String level ,Pageable pageable);

    /**
     * Get by rabbitId, dateInt(ex:20150202), className using page
     * @param rabbitId
     * @param loggingEventDateInt
     * @param className
     * @param pageable
     * @return
     */
    @Query(value = "{ 'rabbitId' : ?0, 'loggingEventDateInt' : ?1, 'loggingEvent.categoryName' : ?2 }")
    public Page<Log> findByRabbitIdAndLoggingEventDateIntAndClassName(String rabbitId, Integer loggingEventDateInt, String className ,Pageable pageable);

    /**
     * Get by rabbitId, dateInt(ex:20150202), level, classname using page
     * @param rabbitId
     * @param loggingEventDateInt
     * @param level
     * @param className
     * @param pageable
     * @return
     */
    @Query(value = "{ 'rabbitId' : ?0, 'loggingEventDateInt' : ?1, 'loggingEvent.level' : ?2, 'loggingEvent.categoryName' : ?3 }")
    public Page<Log> findByRabbitIdAndLoggingEventDateIntAndLevelAndClassName(String rabbitId,
                                                                              Integer loggingEventDateInt,
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
