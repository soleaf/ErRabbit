package org.mintcode.errabbit.core;

import org.mintcode.errabbit.core.report.dao.ReportRepository;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.mintcode.errabbit.core.rabbit.name.RabbitCache;
import org.mintcode.errabbit.core.rabbit.dao.RabbitRepository;
import org.mintcode.errabbit.model.Rabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Core Environment
 * - Start up services
 * Created by soleaf on 2015. 2. 8..
 */

@Component
public class CoreService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitCache nameRepository;

    @Autowired
    private RabbitRepository rabbitRepository;

    @Autowired
    private ReportRepository reportRepository;

    private String systemVersion = "";

    // Unread report count cache
    private Integer unReadReports = 0;

    @PostConstruct
    private void startup(){
        systemVersion = getClass().getPackage().getImplementationVersion();
        logger.info("ErRabbit Service initiating...");
        logger.info("ErRabbit Version {}", systemVersion);
        syncRabbitNameCache();
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    /**
     * Sync Rabbit Names to name dao
     * This Sync need to Log listener check a report's rabbit name is valid.
     */
    public void syncRabbitNameCache(){
        Map<String,Rabbit> rabbits = new HashMap<>();
        List<Rabbit> rabbitList = rabbitRepository.findAll();
        for (Rabbit rabbit : rabbitList){
            rabbits.put(rabbit.getId(),rabbit);
        }
        nameRepository.updateRabbitIdList(rabbits);
        nameRepository.syncDailyStatistics();
    }

    /**
     * Get Unread report count
     * Used on navigation menu report's badge
     */
    public void syncUnreadReportCount(){
        unReadReports = reportRepository.getUnreadCount();
    }
}
