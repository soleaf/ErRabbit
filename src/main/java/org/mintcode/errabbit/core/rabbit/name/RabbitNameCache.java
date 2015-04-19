package org.mintcode.errabbit.core.rabbit.name;

import java.util.List;

/**
 * Rabbit Name Repository
 * Speed access rabbit names
 * Created by soleaf on 2015. 2. 8..
 */
public interface RabbitNameCache {

    /**
     * Init or update rabbitID List
     * @param rabbitIDs
     */
    public void updateRabbitIdList(List<String> rabbitIDs);

    /**
     * Check a rabbit id exists
     * @param id
     * @return
     */
    public boolean isRabbitId(String id);

}
