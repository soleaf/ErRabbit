package org.mintcode.errabbit.controller.console;

import org.mintcode.errabbit.model.ConsoleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by soleaf on 15. 6. 7..
 */
@Controller
@RequestMapping(value = "/console")
public class ConsoleController {

    @RequestMapping(value = "main")
    public String main(Model model){
        return "/console/main";
    }

}
