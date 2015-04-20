package org.mintcode.errabbit.core.rabbit.managing;

import org.apache.log4j.Logger;
import org.mintcode.errabbit.core.rabbit.dao.RabbitRepository;
import org.mintcode.errabbit.core.report.dao.LogLevelDailyStatisticsRepository;
import org.mintcode.errabbit.core.report.dao.ReportRepository;
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
    private RabbitRepository rabbitRepository;

    @Autowired
    private LogLevelDailyStatisticsRepository logLevelDailyStatisticsRepository;

    @Autowired
    private ReportRepository reportRepository;

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public Rabbit makeNewRabbit(String id) throws AlreadyExistRabbitIDException, InvalidRabbitNameException {

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

        Rabbit rabbit = new Rabbit();
        rabbit.setId(id);
        rabbit.setRegDate(new Date());

        rabbitRepository.save(rabbit);

        return rabbit;
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
    public boolean deleteRabbit(String id) {

        // Remove reports
        reportRepository.deleteByRabbitId(id);

        // Remove counters
        logLevelDailyStatisticsRepository.deleteByRabbitId(id);

        // Remove rabbit;
        rabbitRepository.deleteById(id);

        return true;
    }

    @Override
    public Rabbit getRabbitById(String id) {
        return rabbitRepository.findById(id);
    }
}
