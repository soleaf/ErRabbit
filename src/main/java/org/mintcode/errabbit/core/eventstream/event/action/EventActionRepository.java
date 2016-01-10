package org.mintcode.errabbit.core.eventstream.event.action;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by soleaf on 1/8/16.
 */
public interface EventActionRepository extends MongoRepository<EventAction, String> {
}
