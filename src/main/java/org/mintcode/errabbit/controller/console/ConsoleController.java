package org.mintcode.errabbit.controller.console;

import org.mintcode.errabbit.core.report.dao.ReportRepository;
import org.mintcode.errabbit.model.ConsoleMessage;
import org.mintcode.errabbit.model.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by soleaf on 15. 6. 7..
 */
@Controller
@RequestMapping(value = "/console")
public class ConsoleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ReportRepository reportRepository;

    @RequestMapping(value = "main")
    public String main(Model model){
        try{
            Page<Report> page = reportRepository.findAll(new PageRequest(0, 5, new Sort(Sort.Direction.DESC, "_id")));
            model.addAttribute("preload", page);
            return "/console/main";
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            // todo: make ErrorPage
            model.addAttribute("e",e);
            return "/console/main";
        }
    }

    @RequestMapping(value = "session")
    public String extendSession(Model model) {
        return  "/console/session";
    }

}
