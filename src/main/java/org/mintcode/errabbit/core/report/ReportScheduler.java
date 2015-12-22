package org.mintcode.errabbit.core.report;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mintcode.errabbit.core.CoreService;
import org.mintcode.errabbit.core.report.dao.ReportDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * ReportScheduler
 * Created by soleaf on 8/23/15.
 */
@Component
public class ReportScheduler {

    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    ReportDescriptionRepository repository;

    @Autowired
    ReportGenerator generator;

    @Autowired
    CoreService coreService;

    /**
     * Check schedule
     */
    @Scheduled(cron = "0 0 * * * *")
    public void check(){
        try{
            // get descriptions
            List<ReportDescription> descriptionList = repository.findAll();
            if (descriptionList == null && descriptionList.isEmpty()){
                logger.trace("have no report description");
                return;
            }

            // Now, just support one description
            ReportDescription description = descriptionList.get(0);
            if (!description.getActive()){
                logger.trace("description is not active");
                return;
            }

            // Check is time to run report generator
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            Integer nowHour = cal.get(Calendar.HOUR_OF_DAY);
            if (nowHour.equals(description.getTime().getHour())){
                cal.add(Calendar.DAY_OF_MONTH, -1);
                generator.generate(description, cal.getTime());
                coreService.syncUnreadReportCount();
            }
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
        }
    }

}
