package org.mintcode.errabbit.controller.console;

import org.mintcode.errabbit.core.rabbit.managing.RabbitManagingService;
import org.mintcode.errabbit.core.report.ReportDescription;
import org.mintcode.errabbit.core.report.ReportDescriptionTime;
import org.mintcode.errabbit.core.report.dao.ReportDescriptionRepository;
import org.mintcode.errabbit.core.report.dao.ReportRepository;
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

import java.util.List;
import java.util.Set;

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
    ReportRepository reportRepository;

//    @RequestMapping(value = "list")
//    public ModelAndView list(@RequestParam(defaultValue = "0", required = false) Integer page,
//                             @RequestParam(defaultValue = "10", required = false) Integer size){
//        Pageable pageReq = new PageRequest(page, size, Sort.Direction.DESC);
//        reportRepository.findAll()
//
//    }

    @RequestMapping(value = {"settings"})
    public ModelAndView settings(Model model){
        model.addAttribute("rabbits", rabbitManagingService.getRabbits());
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
