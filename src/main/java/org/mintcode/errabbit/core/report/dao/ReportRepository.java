package org.mintcode.errabbit.core.report.dao;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.report.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * ReportRepository
 * Created by soleaf on 8/30/15.
 */
public interface ReportRepository extends MongoRepository<Report, ObjectId> {

    /**
     * Get all unread reports count
     * @return
     */
    @Query(value = "{'read': {'$exists' : false }}", count = true)
    public int getUnreadCount();
}
