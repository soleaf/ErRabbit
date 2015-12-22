package org.mintcode.errabbit.core.rabbit.managing;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQQueue;
import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.CoreService;
import org.mintcode.errabbit.core.log.dao.LogRepository;
import org.mintcode.errabbit.core.rabbit.dao.RabbitGroupRepository;
import org.mintcode.errabbit.core.rabbit.name.RabbitGroupNameComparator;
import org.mintcode.errabbit.core.rabbit.name.RabbitNameComparator;
import org.mintcode.errabbit.model.RabbitGroup;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.mintcode.errabbit.core.rabbit.dao.RabbitRepository;
import org.mintcode.errabbit.core.log.dao.LogLevelDailyStatisticsRepository;
import org.mintcode.errabbit.model.Rabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.util.*;

/**
 * RabbitManagingServiceImpl
 * Created by soleaf on 2015. 1. 8..
 */
@Service
public class RabbitManagingServiceImpl implements RabbitManagingService {

    @Autowired
    private RabbitRepository rabbitRepository;

    @Autowired
    private LogLevelDailyStatisticsRepository logLevelDailyStatisticsRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private CoreService coreService;

    @Autowired
    private RabbitGroupRepository groupRepository;

    @Autowired
    private ConnectionFactory connectionFactory;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RabbitNameComparator rabbitNameComparator = new RabbitNameComparator();
    private RabbitGroupNameComparator rabbitGroupNameComparator = new RabbitGroupNameComparator();

    /**
     * Make new rabbit
     * @param id id
     * @param basePackage option for application base package (ex:org.mintcode.errabbit)
     * @param collectOnlyException option for collection only exception log
     * @param group rabbit group
     * @param hideOnConsole hide on console page
     * @return
     * @throws AlreadyExistRabbitIDException
     * @throws InvalidRabbitNameException
     * @throws InvalidBasePackageException
     */
    @Override
    public Rabbit makeNewRabbit(String id,
                                String basePackage,
                                Boolean collectOnlyException,
                                RabbitGroup group,
                                Boolean hideOnConsole)
            throws AlreadyExistRabbitIDException, InvalidRabbitNameException, InvalidBasePackageException {

        // Rabbit Name Validation
        if (id == null){
            throw new InvalidRabbitNameException(String.format("'Null' is invalid"));
        }
        if (id.length() < 2){
            throw new InvalidRabbitNameException(String.format("Rabbit id's length must be greater than 2 characters."));
        }

        // Check already exist id
        if (rabbitRepository.findById(id) != null) {
            throw new AlreadyExistRabbitIDException(id);
        }

        // BasePackage Validation
        if (basePackage == null || basePackage.length() < 2){
            throw new InvalidBasePackageException(String.format("Please insert base package"));
        }

        Rabbit rabbit = new Rabbit();
        rabbit.setId(id);
        rabbit.setBasePackage(basePackage);
        rabbit.setRegDate(new Date());
        rabbit.setCollectionOnlyException(collectOnlyException);
        rabbit.setGroup(group);
        rabbit.setHideOnConsole(hideOnConsole);

        rabbitRepository.save(rabbit);

        coreService.syncRabbitNameCache();

        return rabbit;
    }

    /**
     * Save to repository and sync with cache
     * @param rabbit
     * @return
     */
    @Override
    public Rabbit saveRabbit(Rabbit rabbit) {
        Rabbit savedRabbit = rabbitRepository.save(rabbit);
        coreService.syncRabbitNameCache();
        return savedRabbit;
    }

    /**
     * Get all rabbits
     * @return
     */
    @Override
    public List<Rabbit> getRabbits() {
        try {
            return rabbitRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * Get rabbit with group
     * @param rabbits
     * @return
     */
    @Override
    public Map<RabbitGroup, Set<Rabbit>> getRabbitsByGroup(List<Rabbit> rabbits) {
        Map<RabbitGroup, Set<Rabbit>> map = new HashMap<>();
        if (rabbits == null){
            rabbits = getRabbits();
        }
        for (Rabbit r : rabbits){
            RabbitGroup group = r.getGroup();
            Set<Rabbit> set = null;
            if (map.containsKey(group)){
                set =  map.get(group);
            }
            else{
                set = new HashSet<>();
                map.put(group, set);
            }
            set.add(r);
        }
        return map;
    }

    /**
     * Get rabbit with sorted group
     * @param rabbitGroupSetMap
     * @return
     */
    public List<RabbitGroup> getRabbitGroupWithRabbitSorted(Map<RabbitGroup, Set<Rabbit>> rabbitGroupSetMap){
        if (rabbitGroupSetMap == null){
            rabbitGroupSetMap = getRabbitsByGroup();
        }
        List<RabbitGroup> list = new ArrayList<>();
        for (RabbitGroup r : rabbitGroupSetMap.keySet()){
            r.setRabbits(new ArrayList<>(rabbitGroupSetMap.get(r)));
            list.add(r);
            Collections.sort(r.getRabbits(), rabbitNameComparator);
        }
        Collections.sort(list, rabbitGroupNameComparator);
        return list;
    }

    /**
     * Get rabbit with group
     * @return
     */
    @Override
    public Map<RabbitGroup, Set<Rabbit>> getRabbitsByGroup() {
        return getRabbitsByGroup(null);
    }

    /**
     * Delete rabbit by rabbitId
     * @param id
     * @return
     */
    @Override
    public boolean deleteRabbit(String id) {

        // Remove reports
        logRepository.deleteByRabbitId(id);

        // Remove counters
        logLevelDailyStatisticsRepository.deleteByRabbitId(id);

        // Remove rabbit;
        rabbitRepository.deleteById(id);

        // Refresh rabbit name cache
        coreService.syncRabbitNameCache();

        // Delete queue from activeMQ
        removeQueue("errabbit.report." +id);

        return true;
    }

    /**
     * Get rabbit by rabbitId
     * @param id
     * @return
     */
    @Override
    public Rabbit getRabbitById(String id) {
        return rabbitRepository.findById(id);
    }

    /**
     * Clean all logs related rabbitId
     * @param id
     * @param begin (ex:20150202)
     * @param end (ex:20150203)
     */
    @Override
    public void cleanLog(String id, Integer begin, Integer end) {
        logRepository.deleteRangeOfLoggingEventDateInt(id, begin, end);
    }

    /**
     * Make new rabbit group
     * @param name
     * @return
     */
    @Override
    public RabbitGroup makeNewGroup(String name) {
        return groupRepository.insert(new RabbitGroup(name));
    }

    /**
     * Save rabbit group to repository
     * @param group
     * @return
     */
    @Override
    public RabbitGroup saveGroup(RabbitGroup group) {
        return groupRepository.save(group);
    }

    /**
     * Get rabbit group by groupId
     * @param id
     * @return
     */
    @Override
    public RabbitGroup getGroup(ObjectId id) {
        return groupRepository.findOne(id);
    }

    /**
     * Delete group by groupId
     * @param group
     * @throws TryToUsedRabbitGroupException
     */
    @Override
    public void deleteGroup(RabbitGroup group) throws TryToUsedRabbitGroupException {

        // Check group is used
        if (getGroups() != null && !getGroups().isEmpty()){
            for (Rabbit rabbit : getRabbits()){
                if (rabbit.getGroup() == null){
                    continue;
                }
                if (rabbit.getGroup().equals(group)){
                    throw new TryToUsedRabbitGroupException(group);
                }
            }
        }

        groupRepository.delete(group);
    }

    /**
     * Get all rabbit groups
     * @return
     */
    @Override
    public List<RabbitGroup> getGroups() {
        return groupRepository.findAll();
    }

    protected boolean removeQueue(String queueName){
        ActiveMQConnection conn = null;
        Boolean result = false;
        try {
            conn = (ActiveMQConnection) connectionFactory.createConnection();
            conn.destroyDestination(new ActiveMQQueue(queueName));
            result = true;
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (JMSException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }  
        return result;
    }
}
