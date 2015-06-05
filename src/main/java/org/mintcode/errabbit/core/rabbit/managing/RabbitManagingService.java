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
    public Rabbit makeNewRabbit(String id,
                                String basePackage,
                                Boolean collectOnlyException) throws AlreadyExistRabbitIDException,
            InvalidRabbitNameException, InvalidBasePackageException;

    /**
     * Save modified rabbit
     * @param rabbit
     */
    public Rabbit saveRabbit(Rabbit rabbit);

    /**
     * Getting Rabbit List
     * @return
     */
    public List<Rabbit> getRabbits();

    /**
     * Delete a Rabbit and related Objects
     * @param id
     * @return
     */
    public boolean deleteRabbit(String id);

    /**
     * Get a Rabbit by ID
     * @param id
     * @return
     */
    public Rabbit getRabbitById(String id);

}
