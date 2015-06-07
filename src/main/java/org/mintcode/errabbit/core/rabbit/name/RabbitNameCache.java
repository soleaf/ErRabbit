package org.mintcode.errabbit.core.rabbit.name;

import org.mintcode.errabbit.model.Rabbit;

import java.util.List;
import java.util.Map;

/**
 * Rabbit Name Repository
 * Speed access rabbit names
 * Created by soleaf on 2015. 2. 8..
 */
public interface RabbitNameCache {

    /**
     * Init or update rabbitID List
     * @param rabbits
     */
    public void updateRabbitIdList(Map<String,Rabbit> rabbits);

    /**
     * Check a rabbit id exists
     * @param id
     * @return
     */
    public boolean isRabbitId(String id);

    /**
     * Get Rabbit by id
     * @param id
     * @return
     */
    public Rabbit getRabbit(String id);

}
