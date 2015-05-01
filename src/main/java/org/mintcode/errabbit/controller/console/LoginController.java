package org.mintcode.errabbit.controller.console;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by soleaf on 2015. 4. 13..
 */
@Controller
public class LoginController {

    private Logger logger = Logger.getLogger(getClass());

    @RequestMapping(value = "/login")
    public String list(Model model,
                             @RequestParam(value = "error", required = false) String error,
                             @RequestParam(value = "logout", required = false) String logout){

        if (error != null) {
            model.addAttribute("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }

        return "/login/login";
    }

}
