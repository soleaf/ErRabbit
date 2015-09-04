package org.mintcode.errabbit.core.rabbit.dao;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.model.RabbitGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by soleaf on 15. 9. 4..
 */
public interface RabbitGroupRepository extends MongoRepository<RabbitGroup, ObjectId>{

}
