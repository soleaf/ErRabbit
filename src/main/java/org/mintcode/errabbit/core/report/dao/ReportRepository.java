package org.mintcode.errabbit.core.report.dao;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Reports Repository
 * Created by soleaf on 2014. 11. 8..
 */

public interface ReportRepository extends MongoRepository<Report,ObjectId>, ReportRepositoryCustom {

    public Page<Report> findByRabbitId(String rabbitId, Pageable pageable);

    public Page<Report> findByRabbitIdAndLoggingEventDateInt(String rabbitId, Integer loggingEventDateInt ,Pageable pageable);

    public List<Report> deleteByRabbitId(String rabbitId);

}
