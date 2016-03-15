package org.mintcode.errabbit.controller.console;

import org.mintcode.errabbit.core.log.dao.LogRepository;
import org.mintcode.errabbit.core.rabbit.name.RabbitCache;
import org.mintcode.errabbit.model.Log;
import org.mintcode.errabbit.model.Rabbit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Receive all logs on one page through web socket.
 * Created by soleaf on 15. 6. 7..
 */
@Controller
@RequestMapping(value = "/console")
public class ConsoleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LogRepository logRepository;

    @Autowired
    RabbitCache rabbitCache;

    // Main UI
    @RequestMapping(value = "main")
    public String main(Model model){
        try{
            List<Log> preloadLogs = logRepository.findInRabbitId(rabbitIdSet(), 5);
            model.addAttribute("preload", preloadLogs);
            return "/console/main";
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            // todo: make ErrorPage
            model.addAttribute("e",e);
            return "/console/main";
        }
    }

    /**
     * RabbitId Set
     * Checking is checked option hide on console
     * @return
     */
    protected Set<String> rabbitIdSet(){
        Set<String> rabbitIdSet = new HashSet<>();
        for (Rabbit rabbit : rabbitCache.getRabbits()){
            if (!rabbit.getHideOnConsole()){
                rabbitIdSet.add(rabbit.getId());
            }
        }
        return rabbitIdSet;
    }

    // Extend login session call (Ajax)
    @RequestMapping(value = "session")
    @ResponseBody
    public String extendSession() {
        return  "ok";
    }

}
