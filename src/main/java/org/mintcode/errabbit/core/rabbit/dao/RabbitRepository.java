package org.mintcode.errabbit.core.rabbit.dao;

import org.mintcode.errabbit.model.Rabbit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository of Rabbits
 * Created by soleaf on 2014. 11. 8..
 */
public interface RabbitRepository extends MongoRepository<Rabbit,String>{

    /**
     * Get by rabbitId
     * @param id
     * @return
     */
    public Rabbit findById(String id);

    /**
     * Delete by rabbitid
     * @param id
     * @return
     */
    public List<Rabbit> deleteById(String id);

}
