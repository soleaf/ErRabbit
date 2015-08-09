package org.mintcode.errabbit.core.log.dao;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Reports Repository
 * Created by soleaf on 2014. 11. 8..
 */

public interface LogRepository extends MongoRepository<Log,ObjectId>, LogRepositoryCustom {

    public Page<Log> findByRabbitId(String rabbitId, Pageable pageable);

    public Page<Log> findByRabbitIdAndLoggingEventDateInt(String rabbitId, Integer loggingEventDateInt ,Pageable pageable);

    public List<Log> deleteByRabbitId(String rabbitId);

}
