package org.mintcode.errabbit.controller.console;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @RequestMapping(value = "mapping/modify")
    public String mappingModify(Model model, @RequestParam String id, RedirectAttributes redirectAttrs) {

        try{
            EventMapping mapping = eventMappingRepository.findOne(id);
            if (mapping == null){
                throw new NullPointerException("Not found mapping");
            }

            // Rabbit list
            model.addAttribute("groups", rabbitManagingService.
                    getRabbitGroupWithRabbitSorted(rabbitManagingService.getRabbitsByGroup(rabbitCache.getRabbits())));
            model.addAttribute("title", "Modify mapping");
            model.addAttribute("action", "/event/mapping/add_action.err");
            model.addAttribute("mapping", mapping);

            if (!mapping.getCondition().getRabbitIdSet().isEmpty()){
                model.addAttribute("rabbitIdSet", StringUtils.join(mapping.getCondition().getRabbitIdSet(),","));
            }
            return "event/mapping_form";
        }
        catch (Exception e){
            redirectAttrs.addAttribute("error", e.getMessage());
            return "redirect:/event/mapping/list.err";
        }
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
                                   @RequestParam(required = false, defaultValue = "False") Boolean exception,
                                   @RequestParam(required = false) String id,
                                   RedirectAttributes redirectAttrs
    ) {

        try{
            EventMapping mapping;
            EventCondition condition;
            if (id == null){
                mapping = new EventMapping();
                condition = new EventCondition();
            }
            else{
                mapping = eventMappingRepository.findOne(id);
                if (id == null){
                    throw new NullPointerException("Not found mapping");
                }
                condition = mapping.getCondition();
            }

            String[] arr = rabbitSet.split(",");
            for (String rabbitId : arr) {
                condition.getRabbitIdSet().clear();
                condition.addRabbitId(rabbitId);
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
            condition.setSleepSecAfterAction(sleep);
            condition.setHasException(exception);
            mapping.setName(name);
            mapping.setActive(active);
            mapping.setCondition(condition);

            if (id == null){
                eventMappingRepository.insert(mapping);
                redirectAttrs.addAttribute("info", "Success creating new mapping");
            }
            else{
                eventMappingRepository.save(mapping);
                redirectAttrs.addAttribute("info", "Success modifying mapping");
            }

            return "redirect:/event/mapping/list.err";
        }
        catch (Exception e){
            redirectAttrs.addAttribute("error", e.getMessage());
            return "redirect:/event/mapping/list.err";
        }

    }

    @RequestMapping(value = "mapping/delete")
    public String mappingDelete(Model model,
                                   @RequestParam(required = false) String id,
                                RedirectAttributes redirectAttrs
    ) {

        try{
            EventMapping mapping = eventMappingRepository.findOne(id);
            if (id == null){
                throw new NullPointerException("Not found mapping");
            }
            eventMappingRepository.delete(mapping);
            redirectAttrs.addAttribute("info","success to delete mapping");
            return "redirect:/event/mapping/list.err";
        }
        catch (Exception e){
            redirectAttrs.addAttribute("error", e.getMessage());
            return "redirect:/event/mapping/list.err";
        }

    }

    @RequestMapping(value = "mapping/action")
    public String mappingAction(Model model, @RequestParam String id, RedirectAttributes redirectAttrs){
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
            redirectAttrs.addAttribute("error",e.getMessage());
            return "redirect:/event/mapping/list.err";
        }
        finally {
            return "event/mapping_action";
        }
    }

    @RequestMapping(value = "mapping/action_action")
    public String mappingActionAction(Model model,
                                            @RequestParam String id,
                                            @RequestParam String actions,
                                      RedirectAttributes redirectAttrs){
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
            redirectAttrs.addAttribute("info", "Success modifying mapping action");
            return "redirect:/event/mapping/list.err";
        }
        catch (Exception e){
            redirectAttrs.addAttribute("error", e.getMessage());
            return "redirect:/event/mapping/list.err";
        }
    }

    @RequestMapping(value = "action/list")
    public ModelAndView actionList(Model model) {
        model.addAttribute("list", eventActionRepository.findAll());
        return new ModelAndView("event/action_list");
    }

    @RequestMapping(value = "action/add")
    public String actionAdd(Model model, @RequestParam() String actionClassName,RedirectAttributes redirectAttrs) {

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
            redirectAttrs.addAttribute("error", e.getMessage());
            return "redirect:/event/action/list.err";
        }
    }

    @RequestMapping(value = "action/add_action")
    public String actionAddAction(Model model,
                                  @RequestParam Map<String, String> param,
                                  RedirectAttributes redirectAttrs) {
        try {
            String actionClassName = param.get("className");
            Class<?> clazz = Class.forName(actionClassName);
            EventAction action = (EventAction) clazz.newInstance();
            action.settingFromUI(param);
            model.addAttribute("addedAction", eventActionRepository.insert(action));
            redirectAttrs.addAttribute("info", "Success adding new action");
            return "redirect:/event/action/list.err";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            redirectAttrs.addAttribute("error", e.getMessage());
            return "redirect:/event/action/list.err";
        }
    }

    @RequestMapping(value = "action/modify")
    public String actionModify(Model model,
                               @RequestParam String id,
                               RedirectAttributes redirectAttrs) {
        try {
            EventAction action = eventActionRepository.findOne(id);
            if (action == null){
                throw new NullPointerException("Not found action");
            }
            model.addAttribute("title", "Modify " + action.getName());
            model.addAttribute("action", "/event/action/modify_action.err");
            model.addAttribute("desc", action.getDescription());
            model.addAttribute("uiElements", action.getUIElements());
            model.addAttribute("eaction",action);
            return "event/action_form";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            redirectAttrs.addAttribute("error", e.getMessage());
            return "redirect:/event/action/list.err";
        }
    }

    @RequestMapping(value = "action/modify_action")
    public String actionModify(Model model,
                               @RequestParam String id,
                               @RequestParam Map<String, String> param,
                               RedirectAttributes redirectAttrs) {
        try {
            EventAction action = eventActionRepository.findOne(id);
            if (action == null){
                throw new NullPointerException("Not found action");
            }
            action.settingFromUI(param);
            model.addAttribute("addedAction", eventActionRepository.save(action));
            redirectAttrs.addAttribute("info", "Success modifying action");
            return "redirect:/event/action/list.err";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            redirectAttrs.addAttribute("error", e.getMessage());
            return "redirect:/event/action/list.err";
        }
    }

    @RequestMapping(value = "action/delete")
    public String actionDelete(Model model, @RequestParam String id, RedirectAttributes redirectAttrs) {
        try {
            EventAction action = eventActionRepository.findOne(id);
            if (action == null){
                throw new NullPointerException("Not found action");
            }
            // Check action is used now
            List<EventMapping> mappings = eventMappingRepository.findAll();
            if (mappings != null && !mappings.isEmpty()){
                for (EventMapping mapping : mappings){
                    if (mapping.getActions().contains(action)){
                        throw new NullPointerException("Actions is used on mapping. You should remove this action from mapping : " + mapping.getName());
                    }
                }
            }
            eventActionRepository.delete(action);
            logger.info("delete action id:"+id);
            redirectAttrs.addAttribute("info", "delete action id:"+id);
            return "redirect:/event/action/list.err?info=" + "success deleting action";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            redirectAttrs.addAttribute("error", e.getMessage());
            return "redirect:/event/action/list.err";
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
