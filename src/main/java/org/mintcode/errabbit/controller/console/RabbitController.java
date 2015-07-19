package org.mintcode.errabbit.controller.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mintcode.errabbit.core.CoreService;
import org.mintcode.errabbit.core.report.dao.LogLevelDailyStatisticsRepository;
import org.mintcode.errabbit.model.LogLevelDailyStatistics;
import org.mintcode.errabbit.model.Rabbit;
import org.mintcode.errabbit.core.rabbit.managing.RabbitManagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rabbit Managing Controller
 * Created by soleaf on 2014. 11. 9..
 */
@Controller
@RequestMapping(value = "/rabbit")
public class RabbitController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RabbitManagingService rabbitManagingService;

    @Autowired
    LogLevelDailyStatisticsRepository logLevelDailyStatisticsRepository;

    // List of all Rabbits
    @RequestMapping(value = {"list"})
    public ModelAndView list(Model model,
                             @RequestParam(value = "info", required = false) String info,
                             @RequestParam(value = "error", required = false) String error) {

        try {
            List<Rabbit> rabbitList = rabbitManagingService.getRabbits();
            Map<Rabbit, LogLevelDailyStatistics> lastStatics = new HashMap<Rabbit, LogLevelDailyStatistics>();
            for (Rabbit rabbit : rabbitList) {
                LogLevelDailyStatistics statistics = logLevelDailyStatisticsRepository.findByRabbitIdOnLast(rabbit.getId());
                if (statistics != null) {
                    lastStatics.put(rabbit, statistics);
                }
            }
            model.addAttribute("list", rabbitList);
            model.addAttribute("lastStatics", lastStatics);
            model.addAttribute("info", info);
            model.addAttribute("error", error);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
        }

        return new ModelAndView("/rabbit/list");
    }

    // New Rabbit form
    @RequestMapping(value = "insert")
    public ModelAndView insertForm() {
        return new ModelAndView("/rabbit/form");
    }

    // Action for Insert a rabbit
    @RequestMapping(value = "insert_action")
    public String insertAction(@RequestParam(value = "id", required = true) String id,
                               @RequestParam(value = "basePackage", required = true) String basePackage,
                               @RequestParam(value = "onlyException", required = false, defaultValue = "false") Boolean onlyException,
                               Model model) {
        try {
            Rabbit newRabbit = rabbitManagingService.makeNewRabbit(id, basePackage, onlyException);
            logger.info("Made new Rabbit > " + newRabbit);

            model.addAttribute("info", String.format("Success to make Rabbit '%s'", id));
            return "redirect:list.err";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("e", e);
            return "/rabbit/form";
        }
    }

    // Modify Rabbit form
    @RequestMapping(value = "modify")
    public String modifyForm(@RequestParam(value = "id", required = true) String id,
                             Model model){

        model.addAttribute("modifying", true);

        try {
            Rabbit rabbit = rabbitManagingService.getRabbitById(id);
            if (rabbit == null) {
                throw new RabbitNotExistException(id);
            }
            model.addAttribute(rabbit);
            return "/rabbit/form";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("e", e);
            return "/rabbit/form";
        }
    }

    // Action for Modify a rabbit
    @RequestMapping(value = "modify_action")
    public String modifyAction(@RequestParam(value = "id", required = true) String id,
                               @RequestParam(value = "basePackage", required = true) String basePackage,
                               @RequestParam(value = "onlyException", required = false, defaultValue = "false") Boolean onlyException,
                               Model model) {
        try {

            Rabbit rabbit = rabbitManagingService.getRabbitById(id);
            if (rabbit == null) {
                throw new RabbitNotExistException(id);
            }
            rabbit.setBasePackage(basePackage);
            rabbit.setCollectionOnlyException(onlyException);
            Rabbit savedRabbit = rabbitManagingService.saveRabbit(rabbit);

            logger.info("Modify Rabbit > " + savedRabbit);
            model.addAttribute("info", String.format("Success to modify Rabbit '%s'", id));
            return "redirect:list.err";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("e", e);
            model.addAttribute("modifying", true);
            return "/rabbit/form";
        }
    }

    // Delete a rabbit
    @RequestMapping(value = "delete")
    public String delete(@RequestParam(value = "id", required = true) String id,
                         Model model) {
        try {

            Rabbit rabbit = rabbitManagingService.getRabbitById(id);
            if (rabbit == null) {
                throw new RabbitNotExistException(id);
            }

            logger.info("Delete Rabbit > " + id);

            // Delete
            rabbitManagingService.deleteRabbit(id);

            model.addAttribute("info", String.format("Success to delete Rabbit '%s'", id));
            return "redirect:list.err?";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            return "redirect:list.err";
        }
    }

    // Delete logs on range
    @RequestMapping(value = "clean")
    public String delete(@RequestParam(value = "id", required = true) String id,
                         @RequestParam(value = "begin", required = true) String begin,
                         @RequestParam(value = "end", required = true) String end,
                         Model model) {
        try {

            Rabbit rabbit = rabbitManagingService.getRabbitById(id);
            if (rabbit == null) {
                throw new RabbitNotExistException(id);
            }

            Integer beginDateInteger = Integer.parseInt(begin.replace("-",""));
            Integer endDateInteger = Integer.parseInt(end.replace("-",""));

            logger.info("Clean log for rabbit > " + id);

            // Clean logs
            rabbitManagingService.cleanLog(id, beginDateInteger, endDateInteger);

            // Clean logLevelDailyStatistics
            logLevelDailyStatisticsRepository.deleteDailyStatisticRangeOfLoggingEventDateInt(id,
                    beginDateInteger, endDateInteger);

            model.addAttribute("info", String.format("Success to clean Rabbit '%s'", id));
            return "redirect:list.err?";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            return "redirect:list.err";
        }
    }

    // How to Integrate with Application
    @RequestMapping(value = "howto")
    public String howTo() {
        return "/rabbit/setup";
    }

    public class RabbitNotExistException extends Exception {
        public RabbitNotExistException(String id) {
            super(String.format("Rabbit '%s' is not exist", id));
        }
    }

}
