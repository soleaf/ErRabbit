package org.mintcode.errabbit.core.notification;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.eventstream.event.EventSetting;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by soleaf on 10/12/15.
 */
public interface NotificationSettingRepository extends MongoRepository<EventSetting, ObjectId>{

    public List<EventSetting> findByActive(Boolean active);
}
