package org.mintcode.errabbit.controller.console;

import org.mintcode.errabbit.core.eventstream.EventStreamCentral;
import org.mintcode.errabbit.core.eventstream.event.EventCondition;
import org.mintcode.errabbit.core.eventstream.event.EventMapping;
import org.mintcode.errabbit.core.eventstream.event.EventMappingRepository;
import org.mintcode.errabbit.core.eventstream.event.action.EventAction;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private EventStreamCentral eventStreamCentral;

    @RequestMapping(value = "mapping/list")
    public ModelAndView mappingList(Model model) {
        model.addAttribute("list", eventMappingRepository.findAll());
        return new ModelAndView("event/mapping_list");
    }

    @RequestMapping(value = "mapping/add")
    public ModelAndView mappingAdd(Model model) {
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
    ) {

        EventCondition condition = new EventCondition();
        String[] arr = rabbitSet.split(",");
        for (String id : arr) {
            condition.addRabbitId(id);
        }
        condition.setMatchLevel(level);
        if (matchClass.length() > 0) {
            condition.setMatchClass(matchClass);
        }
        if (message.length() > 0) {
            condition.setIncludeMessage(message);
        }
        condition.setThresholdCount(thresholdCount);
        condition.setPeriodSec(period);
        if (sleep > 0) {
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

    @RequestMapping(value = "mapping/action")
    public ModelAndView mappingAction(Model model, @RequestParam String id){
        try{
            EventMapping mapping = eventMappingRepository.findOne(id);
            if (mapping == null){
                throw new NullPointerException("Can't find mapping for id:"+id);
            }

            List<EventAction> allActions = eventActionRepository.findAll();
            List<EventAction> avaliableActions = new ArrayList<>();
            if (!allActions.isEmpty()){
                for (EventAction action : allActions){
                    if (!mapping.getActions().contains(action)){
                        avaliableActions.add(action);
                    }
                }
            }

            model.addAttribute("mapping", mapping);
            model.addAttribute("hasActions", mapping.getActions());
            model.addAttribute("allActions", avaliableActions);
            model.addAttribute("action", "action_action.err");
        }
        catch (Exception e){
            // todo: error page
            model.addAttribute("error",e);
        }
        finally {
            return new ModelAndView("event/mapping_action");
        }
    }

    @RequestMapping(value = "mapping/action_action")
    public String mappingActionAction(Model model,
                                            @RequestParam String id,
                                            @RequestParam String actions){
        try{
            EventMapping mapping = eventMappingRepository.findOne(id);
            if (mapping == null){
                throw new NullPointerException("Can't find mapping for id:"+id);
            }

            // Clear
            mapping.getActions().clear();

            // Add or modify
            if (actions.length() > 0){
                String[] actionArr = actions.split(",");
                for (String actionId : actionArr){
                    EventAction action = eventActionRepository.findOne(actionId);
                    if (action == null){
                        throw new NullPointerException("Can't find action for id:"+actionId);
                    }
                    mapping.getActions().add(action);
                }
            }

            // Save
            eventMappingRepository.save(mapping);
            return "redirect:/event/mapping/list.err";
        }
        catch (Exception e){
            return "redirect:/event/mapping/list.err?error="+e.getMessage();
        }
    }

    @RequestMapping(value = "action/list")
    public ModelAndView actionList(Model model) {
        model.addAttribute("list", eventActionRepository.findAll());
        return new ModelAndView("event/action_list");
    }

    @RequestMapping(value = "action/add")
    public String actionAdd(Model model, @RequestParam() String actionClassName) {

        try {
            Class<?> clazz = Class.forName(actionClassName);
            EventAction action = (EventAction) clazz.newInstance();

            model.addAttribute("title", "New " + action.getTypeName());
            model.addAttribute("action", "/event/action/add_action.err");
            model.addAttribute("desc", action.getDescription());
            model.addAttribute("uiElements", action.getUIElements());
            return "event/action_form";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "redirect:/event/action/list.err?error=" + e.getMessage();
        }
    }

    @RequestMapping(value = "action/add_action")
    public String actionAddAction(Model model, @RequestParam Map<String, String> param, HttpServletRequest request) {
        try {
            String actionClassName = param.get("className");
            Class<?> clazz = Class.forName(actionClassName);
            EventAction action = (EventAction) clazz.newInstance();
            action.settingFromUI(param);
            model.addAttribute("addedAction", eventActionRepository.insert(action));
            return "redirect:/event/action/list.err";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "redirect:" + request.getHeader("Referer") + "?error=" + e.getMessage();
        }
    }

    @RequestMapping(value = "es/rebuild")
    public ModelAndView dashboard(Model model){
        model.addAttribute("ec", eventStreamCentral);
        model.addAttribute("es", eventStreamCentral.makeEventStream());
        return new ModelAndView("event/rebuild");
    }

    @RequestMapping(value = "es/rebuild_action")
    public String dashboardAction(Model model){
        eventStreamCentral.build();
        return "redirect:rebuild.err";
    }

}
