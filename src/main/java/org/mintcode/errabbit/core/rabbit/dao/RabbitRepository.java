package org.mintcode.errabbit.core.rabbit.dao;

import org.mintcode.errabbit.model.Rabbit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of Rabbits
 * Created by soleaf on 2014. 11. 8..
 */

public interface RabbitRepository extends MongoRepository<Rabbit,String>{

    public Rabbit findById(String id);

    public List<Rabbit> deleteById(String id);

}
