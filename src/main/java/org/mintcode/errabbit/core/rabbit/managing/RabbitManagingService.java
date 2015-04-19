package org.mintcode.errabbit.core.rabbit.managing;

import org.mintcode.errabbit.model.Rabbit;

import java.util.List;

/**
 * RabbitManagingService
 * Created by soleaf on 2015. 1. 8..
 */
public interface RabbitManagingService {

    /**
     * Make New Rabbit
     * @param id id
     * @return
     */
    public Rabbit makeNewRabbit(String id) throws AlreadyExistRabbitIDException;

    /**
     * Getting Rabbit List
     * @return
     */
    public List<Rabbit> getRabbits();

}
