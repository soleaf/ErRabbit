package org.mintcode.errabbit.core.eventstream.event;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by soleaf on 1/8/16.
 */
public interface EventMappingRepository extends MongoRepository<EventMapping, String> {

}
