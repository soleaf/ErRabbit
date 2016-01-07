package org.mintcode.errabbit.core.notification;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.eventstream.event.EventCondition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by soleaf on 10/12/15.
 */
public interface NotificationSettingRepository extends MongoRepository<EventCondition, ObjectId>{

    public List<EventCondition> findByActive(Boolean active);
}
