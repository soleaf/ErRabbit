package org.mintcode.errabbit.core.rabbit.managing;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.model.LoggerType;
import org.mintcode.errabbit.model.Rabbit;
import org.mintcode.errabbit.model.RabbitGroup;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RabbitManagingService
 * Managing service related rabbit
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
                                Boolean collectOnlyException,
                                RabbitGroup group,
                                Boolean hideOnConsole,
                                LoggerType loggerType) throws AlreadyExistRabbitIDException,
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
     * Getting Rabbit group by RabbitGroup
     * @return
     */
    public Map<RabbitGroup, Set<Rabbit>> getRabbitsByGroup(List<Rabbit> rabbits);

    /**
     * Getting Rabbit group by RabbitGroup
     * @return
     */
    public Map<RabbitGroup, Set<Rabbit>> getRabbitsByGroup();

    /**
     * Getting Rabbit group by RabbitGroup and sort.
     * @param rabbitGroupSetMap
     * @return
     */
    public List<RabbitGroup> getRabbitGroupWithRabbitSorted(Map<RabbitGroup, Set<Rabbit>> rabbitGroupSetMap);

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

    /**
     * Clean log
     * @param id
     * @param begin
     * @param end
     */
    public void cleanLog(String id, Integer begin, Integer end);

    /**
     * New rabbit group with name
     * @param name
     */
    public RabbitGroup makeNewGroup(String name);

    /**
     * Save rabbit group
     * @param group
     * @return
     */
    public RabbitGroup saveGroup(RabbitGroup group);

    /**
     * Find rabbir group by ObjectId
     * @param id
     * @return
     */
    public RabbitGroup getGroup(ObjectId id);

    /**
     * delete rabbit group
     * with check group has been used.
     * @param group
     * @return
     */
    public void deleteGroup(RabbitGroup group) throws TryToUsedRabbitGroupException;

    /**
     * Get all rabbit groups
     * @return
     */
    public List<RabbitGroup> getGroups();
}
