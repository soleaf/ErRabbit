package org.mintcode.errabbit.controller.console;

import org.mintcode.errabbit.core.eventstream.event.EventCondition;
import org.mintcode.errabbit.core.eventstream.event.EventMapping;
import org.mintcode.errabbit.core.eventstream.event.EventMappingRepository;
import org.mintcode.errabbit.core.eventstream.event.action.EventActionRepository;
import org.mintcode.errabbit.core.rabbit.managing.RabbitManagingService;
import org.mintcode.errabbit.core.rabbit.name.RabbitCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by soleaf on 1/8/16.
 */
@Controller
@RequestMapping(value = "/event")
public class EventController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventActionRepository eventActionRepository;

    @Autowired
    private EventMappingRepository eventMappingRepository;

    @Autowired
    private RabbitCache rabbitCache;

    @Autowired
    private RabbitManagingService rabbitManagingService;

    @RequestMapping(value = "mapping/list")
    public ModelAndView mappingList(Model model){
        model.addAttribute("list",eventMappingRepository.findAll());
        return new ModelAndView("event/mapping_list");
    }

    @RequestMapping(value = "mapping/add")
    public ModelAndView mappingAdd(Model model){
        // Rabbit list
        model.addAttribute("groups", rabbitManagingService.
                getRabbitGroupWithRabbitSorted(rabbitManagingService.getRabbitsByGroup(rabbitCache.getRabbits())));
        model.addAttribute("title", "New mapping");
        model.addAttribute("action", "/event/mapping/add_action.err");
        return new ModelAndView("event/mapping_form");
    }

    @RequestMapping(value = "mapping/add_action")
    public String mappingAddAction(Model model,
                                   @RequestParam(required = true) String name,
                                   @RequestParam(required = true) String rabbitSet,
                                   @RequestParam(required = true) String level,
                                   @RequestParam(required = false) String matchClass,
                                   @RequestParam(required = false) String message,
                                   @RequestParam(required = true) Integer thresholdCount,
                                   @RequestParam(required = true) Integer period,
                                   @RequestParam(required = true) Integer sleep,
                                   @RequestParam(required = false, defaultValue = "False") Boolean active,
                                   @RequestParam(required = false, defaultValue = "False") Boolean exception
                                   ){

        EventCondition condition = new EventCondition();
        String[] arr = rabbitSet.split(",");
        for (String id : arr){
            condition.addRabbitId(id);
        }
        condition.setMatchLevel(level);
        if (matchClass.length() > 0){
            condition.setMatchClass(matchClass);
        }
        if(message.length() > 0){
            condition.setIncludeMessage(message);
        }
        condition.setThresholdCount(thresholdCount);
        condition.setPeriodSec(period);
        if (sleep > 0){
            condition.setSleepSecAfterAction(sleep);
        }
        condition.setHasException(exception);

        EventMapping mapping = new EventMapping();
        mapping.setName(name);
        mapping.setActive(active);
        mapping.setCondition(condition);

        eventMappingRepository.insert(mapping);

        return "redirect:/event/mapping/list.err";
    }

    @RequestMapping(value = "action/list")
    public ModelAndView actionList(Model model){
        model.addAttribute("list",eventActionRepository.findAll());
        return new ModelAndView("event/action_list");
    }

    @RequestMapping(value = "action/add")
    public ModelAndView actionAdd(Model model){
        // Rabbit list
        model.addAttribute("groups", rabbitManagingService.
                getRabbitGroupWithRabbitSorted(rabbitManagingService.getRabbitsByGroup(rabbitCache.getRabbits())));
        model.addAttribute("title", "New action");
        model.addAttribute("action", "/event/action/add_action.err");
        return new ModelAndView("event/action_form");
    }

    @RequestMapping(value = "action/add_action")
    public String actionAddAction(Model model,
                             @RequestParam(required = true) String name,
                             @RequestParam(required = true) String rabbitSet,
                             @RequestParam(required = true) String level,
                             @RequestParam(required = false) String matchClass,
                             @RequestParam(required = false) String message,
                             @RequestParam(required = true) Integer thresholdCount,
                             @RequestParam(required = true) Integer period,
                             @RequestParam(required = true) Integer sleep,
                             @RequestParam(required = false, defaultValue = "False") Boolean active,
                             @RequestParam(required = false, defaultValue = "False") Boolean exception
    ){

        EventCondition condition = new EventCondition();
        String[] arr = rabbitSet.split(",");
        for (String id : arr){
            condition.addRabbitId(id);
        }
        condition.setMatchLevel(level);
        if (matchClass.length() > 0){
            condition.setMatchClass(matchClass);
        }
        if(message.length() > 0){
            condition.setIncludeMessage(message);
        }
        condition.setThresholdCount(thresholdCount);
        condition.setPeriodSec(period);
        if (sleep > 0){
            condition.setSleepSecAfterAction(sleep);
        }
        condition.setHasException(exception);

        EventMapping mapping = new EventMapping();
        mapping.setName(name);
        mapping.setActive(active);
        mapping.setCondition(condition);

        eventMappingRepository.insert(mapping);

        return "redirect:/event/mapping/list.err";
    }

}
