package org.mintcode.errabbit.core.rabbit.name;

import org.mintcode.errabbit.model.Rabbit;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soleaf on 2015. 2. 8..
 */
@Repository
public class InMemoryRabbitNameCache implements RabbitNameCache {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String,Rabbit> rabbits;

    public void updateRabbitIdList(Map<String,Rabbit> rabbits) {
        this.rabbits = rabbits;
        logger.trace("Rabbit Id List updated. size=" + rabbits.size());
    }

    public boolean isRabbitId(String id) {
        if (rabbits == null){
            logger.error("Rabbit Id List null.");
            return false;
        }
        return rabbits.keySet().contains(id);
    }

    public Rabbit getRabbit(String id){
        if (rabbits == null){
            logger.error("Rabbit Id List null.");
            return null;
        }
        return rabbits.get(id);
    }
}
