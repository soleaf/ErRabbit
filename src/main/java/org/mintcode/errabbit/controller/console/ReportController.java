package org.mintcode.errabbit.controller.console;

import org.mintcode.errabbit.core.rabbit.managing.RabbitManagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by soleaf on 15. 8. 9..
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RabbitManagingService rabbitManagingService;

    @RequestMapping(value = {"settings"})
    public ModelAndView list(Model model){
        model.addAttribute("rabbits", rabbitManagingService.getRabbits());
        return new ModelAndView("/report/form");
    }

}
