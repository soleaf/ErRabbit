package org.mintcode.errabbit.core.rabbit.managing;

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

import java.util.*;

/**
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RabbitNameComparator rabbitNameComparator = new RabbitNameComparator();
    private RabbitGroupNameComparator rabbitGroupNameComparator = new RabbitGroupNameComparator();


    @Override
    public Rabbit makeNewRabbit(String id,
                                String basePackage,
                                Boolean collectOnlyException) throws AlreadyExistRabbitIDException,
            InvalidRabbitNameException, InvalidBasePackageException {
            return makeNewRabbit(id, basePackage, collectOnlyException, null);
    }

    @Override
    public Rabbit makeNewRabbit(String id, String basePackage, Boolean collectOnlyException, RabbitGroup group) throws AlreadyExistRabbitIDException, InvalidRabbitNameException, InvalidBasePackageException {

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

        rabbitRepository.save(rabbit);

        coreService.syncRabbitNameCache();

        return rabbit;
    }

    @Override
    public Rabbit saveRabbit(Rabbit rabbit) {
        Rabbit savedRabbit = rabbitRepository.save(rabbit);
        coreService.syncRabbitNameCache();
        return savedRabbit;
    }

    @Override
    public List<Rabbit> getRabbits() {
        try {
            return rabbitRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

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

    @Override
    public Map<RabbitGroup, Set<Rabbit>> getRabbitsByGroup() {
        return getRabbitsByGroup(null);
    }

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

        return true;
    }

    @Override
    public Rabbit getRabbitById(String id) {
        return rabbitRepository.findById(id);
    }

    @Override
    public void cleanLog(String id, Integer begin, Integer end) {
        logRepository.deleteReportRangeOfLoggingEventDateInt(id, begin, end);
    }

    @Override
    public RabbitGroup makeNewGroup(String name) {
        return groupRepository.insert(new RabbitGroup(name));
    }

    @Override
    public RabbitGroup saveGroup(RabbitGroup group) {
        return groupRepository.save(group);
    }

    @Override
    public RabbitGroup getGroup(ObjectId id) {
        return groupRepository.findOne(id);
    }

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

    @Override
    public List<RabbitGroup> getGroups() {
        return groupRepository.findAll();
    }

}
