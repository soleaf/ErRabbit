package org.mintcode.errabbit.core.report.dao;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.report.ReportDescription;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ReportDescriptionRepository
 * Created by soleaf on 8/23/15.
 */
public interface ReportDescriptionRepository extends MongoRepository<ReportDescription, ObjectId> {

}
