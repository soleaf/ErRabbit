package org.mintcode.errabbit.controller.console;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.log.dao.LogLevelHourlyStatisticsRepository;
import org.mintcode.errabbit.core.rabbit.managing.RabbitManagingService;
import org.mintcode.errabbit.core.report.Report;
import org.mintcode.errabbit.core.report.ReportDescription;
import org.mintcode.errabbit.core.report.ReportDescriptionTime;
import org.mintcode.errabbit.core.report.dao.ReportDescriptionRepository;
import org.mintcode.errabbit.core.report.dao.ReportRepository;
import org.mintcode.errabbit.model.LogLevelHourStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by soleaf on 15. 8. 9..
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RabbitManagingService rabbitManagingService;

    @Autowired
    ReportDescriptionRepository descriptionRepository;

    @Autowired
    LogLevelHourlyStatisticsRepository logLevelHourlyStatisticsRepository;

    @Autowired
    ReportRepository reportRepository;

    @RequestMapping(value = {"list"})
    public ModelAndView list(Model model){
        return new ModelAndView("/report/list");
    }

    @RequestMapping(value = "list_data")
    public ModelAndView listData(@RequestParam(defaultValue = "0", required = false) Integer page,
                                 @RequestParam(defaultValue = "50", required = false) Integer size,
                                 Model model){
        try{
            Pageable pageReq = new PageRequest(page, size, Sort.Direction.DESC, "_id");
            model.addAttribute("list", reportRepository.findAll(pageReq));
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            model.addAttribute("e",e.getMessage());
        }
        return new ModelAndView("/report/list_data");
    }

    @RequestMapping(value = {"detail"})
    public ModelAndView detail(@RequestParam String id,
                               Model model){

        try{
            // Report
            Report report = reportRepository.findOne(new ObjectId(id));
            model.addAttribute("report",report);

            // Sum logHourlySet
            Map<Integer, LogLevelHourStatistics> logLevelhourlySet = new HashMap<>();
            for (int i=0; i < 24; i++){
                LogLevelHourStatistics hour = new LogLevelHourStatistics();
                hour.setHour(i);
                logLevelhourlySet.put(i,hour);
            }
            model.addAttribute("logLevelhourlySet", logLevelhourlySet);

            // LogHourlySet by rabbit
            DateFormat format = new SimpleDateFormat("yyyyMMdd");
            Integer dateInt = Integer.parseInt(format.format(report.getTargetDate()));
            Map<String, Map<Integer, LogLevelHourStatistics>> rabbitLevelHourlySet = new HashMap<>();
            for (String rabbit : report.getTargets()){

                // add statistics
                Map<Integer, LogLevelHourStatistics> levelHourlySet = new HashMap<>();
                List<LogLevelHourStatistics> source = logLevelHourlyStatisticsRepository.findByRabbitIdAndDateInt(rabbit, dateInt);
                if (source != null && !source.isEmpty()){
                    for (LogLevelHourStatistics statics : source){
                        levelHourlySet.put(statics.getHour(), statics);
                        // add sum
                        logLevelhourlySet.get(statics.getHour()).add(statics);
                    }
                }

                // Add 0 to blank time
                for (int i=0 ; i<24; i++){
                    if (!levelHourlySet.containsKey(i)){
                        levelHourlySet.put(i, new LogLevelHourStatistics());
                    }
                }
                rabbitLevelHourlySet.put(rabbit, levelHourlySet);
            }
            model.addAttribute("rabbitLevelHourlySet", rabbitLevelHourlySet);

            // Mark read
            report.setRead(true);
            reportRepository.save(report);
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            model.addAttribute("e",e.getMessage());
        }
        return new ModelAndView("/report/detail");
    }
    @RequestMapping(value = {"delete"})
    public ModelAndView delete(@RequestParam String id,
                               Model model){
        try{
            // Report
            Report report = reportRepository.findOne(new ObjectId(id));
            reportRepository.delete(report);
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            model.addAttribute("e",e.getMessage());
        }
        return new ModelAndView("/report/list");
    }

    @RequestMapping(value = {"settings"})
    public ModelAndView settings(Model model){
        model.addAttribute("groups", rabbitManagingService.
                getRabbitGroupWithRabbitSorted(rabbitManagingService.getRabbitsByGroup()));
        List<ReportDescription> descriptions = descriptionRepository.findAll();
        if (descriptions != null && !descriptions.isEmpty()){
            model.addAttribute("description", descriptions.get(0));
        }
        return new ModelAndView("/report/form");
    }

    @RequestMapping(value = {"settings_action"} , method = RequestMethod.POST)
    public String settingAction(Model model,
                                @RequestParam Set<String> targets,
                                @RequestParam(required = false) Set<String> subscribers,
                                @RequestParam(required = false, defaultValue = "False") Boolean active,
                                @RequestParam Integer timeHour,
                                @RequestParam(required = false) String smtpHost,
                                @RequestParam(required = false) String stmpId,
                                @RequestParam(required = false) String smtpPassword,
                                @RequestParam(required = false) Integer smtpTLSPort,
                                @RequestParam(required = false) Integer smtpSSL,
                                @RequestParam(required = false) Boolean smtpUserAuth
                                ){
        List<ReportDescription> descriptions = descriptionRepository.findAll();
        ReportDescription description;
        if (descriptions != null && !descriptions.isEmpty()){
            description = descriptions.get(0);
        } else {
            description = new ReportDescription();
        }

        description.setSubscribers(subscribers);
        description.setTargets(targets);
        description.setActive(active);
        description.setTime(new ReportDescriptionTime(timeHour));

        logger.debug("save report description : " + description);

        if (description.getId() != null)
            descriptionRepository.save(description);
        else
            descriptionRepository.insert(description);

        return "redirect:settings.err";
    }

}
