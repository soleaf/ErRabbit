package org.mintcode.errabbit.core.report.dao;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.report.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by soleaf on 8/30/15.
 */
public interface ReportRepository extends MongoRepository<Report, ObjectId> {
}
