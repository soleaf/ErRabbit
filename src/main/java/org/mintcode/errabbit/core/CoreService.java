package org.mintcode.errabbit.core;

import org.apache.log4j.Logger;
import org.mintcode.errabbit.core.rabbit.name.RabbitNameCache;
import org.mintcode.errabbit.core.rabbit.dao.RabbitRepository;
import org.mintcode.errabbit.model.Rabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Make Environment for rabbit core.
 * Created by soleaf on 2015. 2. 8..
 */

@Component
public class CoreService {

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private RabbitNameCache nameRepository;
    @Autowired
    private RabbitRepository rabbitRepository;

    @PostConstruct
    private void startup(){

        logger.debug("ErRabbit Service initiating...");

        syncRabbitNameCache();
    }

    /**
     * Sync Rabbit Names to name dao
     * This Sync need to Report listener check a report's rabbit name is valid.
     */
    public void syncRabbitNameCache(){
        List<Rabbit> rabbitList = rabbitRepository.findAll();
        List<String> nameList = new ArrayList<String>();
        for (Rabbit rabbit : rabbitList){
            nameList.add(rabbit.getId());
        }
        nameRepository.updateRabbitIdList(nameList);
    }


}
