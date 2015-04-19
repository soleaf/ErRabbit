package org.mintcode.errabbit.core.rabbit.managing;

import org.apache.log4j.Logger;
import org.mintcode.errabbit.core.rabbit.dao.RabbitRepository;
import org.mintcode.errabbit.model.Rabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by soleaf on 2015. 1. 8..
 */
@Service
public class RabbitManagingServiceImpl implements RabbitManagingService {

    @Autowired
    private RabbitRepository repository;

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public Rabbit makeNewRabbit(String id) throws AlreadyExistRabbitIDException {

        // Check already exist id
        if (repository.findById(id) != null) {
            throw new AlreadyExistRabbitIDException(id);
        }

        Rabbit rabbit = new Rabbit();
        rabbit.setId(id);
        rabbit.setRegDate(new Date());
        return rabbit;
    }

    @Override
    public List<Rabbit> getRabbits() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return null;
        }

    }
}
