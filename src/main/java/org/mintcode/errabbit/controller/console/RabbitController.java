package org.mintcode.errabbit.controller.console;

import org.apache.log4j.Logger;
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

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    RabbitManagingService rabbitManagingService;

    @Autowired
    LogLevelDailyStatisticsRepository logLevelDailyStatisticsRepository;

    @Autowired
    CoreService coreService;

    // List of all Rabbits
    @RequestMapping(value = "list")
    public ModelAndView list(Model model,
                             @RequestParam(value = "info", required = false) String info,
                             @RequestParam(value = "error", required = false) String error){

        try{
            List<Rabbit> rabbitList = rabbitManagingService.getRabbits();
            Map<Rabbit,LogLevelDailyStatistics> lastStatics = new HashMap<Rabbit,LogLevelDailyStatistics>();
            for (Rabbit rabbit : rabbitList){
                LogLevelDailyStatistics statistics = logLevelDailyStatisticsRepository.findByRabbitIdOnLast(rabbit.getId());
                if (statistics != null){
                    lastStatics.put(rabbit,statistics);
                }
            }
            model.addAttribute("list", rabbitList);
            model.addAttribute("lastStatics", lastStatics);
            model.addAttribute("info",info);
            model.addAttribute("error",error);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            model.addAttribute("error", e.getMessage());
        }

        return new ModelAndView("/rabbit/list");
    }

    // New Rabbit form
    @RequestMapping(value = "insert")
    public ModelAndView insertForm(){
        return new ModelAndView("/rabbit/form");
    }

    // Action for Insert a rabbit
    @RequestMapping(value = "insert_action")
    public String insertAction(@RequestParam(value = "id", required = true) String id,
                               Model model){
        try{
            Rabbit newRabbit = rabbitManagingService.makeNewRabbit(id);
            logger.info("Made new Rabbit > " + newRabbit);

            // update rabbit name cache
            coreService.syncRabbitNameCache();

            model.addAttribute("info", String.format("Success to make Rabbit '%s'", id));
            return "redirect:list.err";
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            model.addAttribute("e",e);
            return "/rabbit/form";
        }
    }

    // Delete a rabbit
    @RequestMapping(value = "delete")
    public String delete(@RequestParam(value = "id", required = true) String id,
                               Model model){
        try{

            Rabbit rabbit = rabbitManagingService.getRabbitById(id);
            if (rabbit == null){
                throw new RabbitNotExistException(id);
            }

            logger.info("Delete Rabbit > " + id);

            // Delete
            rabbitManagingService.deleteRabbit(id);

            // update rabbit name cache
            coreService.syncRabbitNameCache();

            model.addAttribute("info", String.format("Success to delete Rabbit '%s'", id));
            return "redirect:list.err?";
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            model.addAttribute("error",e.getMessage());
            return "redirect:list.err";
        }
    }

    public class RabbitNotExistException extends Exception{
        public RabbitNotExistException(String id) {
            super(String.format("Rabbit '%s' is not exist", id));
        }
    }




}
