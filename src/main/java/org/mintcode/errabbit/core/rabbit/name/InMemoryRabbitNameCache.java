package org.mintcode.errabbit.core.rabbit.name;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by soleaf on 2015. 2. 8..
 */
@Repository
public class InMemoryRabbitNameCache implements RabbitNameCache {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<String> rabbitIDList;

    public void updateRabbitIdList(List<String> rabbitIDs) {
        this.rabbitIDList = rabbitIDs;
        logger.trace("Rabbit Id List updated. size=" + rabbitIDs.size());
    }

    public boolean isRabbitId(String id) {

        if (rabbitIDList == null){
            logger.error("Rabbit Id List null.");
            return false;
        }

        return rabbitIDList.contains(id);
    }
}
