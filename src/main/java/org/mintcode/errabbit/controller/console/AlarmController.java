package org.mintcode.errabbit.controller.console;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by soleaf on 15. 5. 10..
 */
@Controller
@RequestMapping("/alarm")
public class AlarmController {

    @RequestMapping(value = "intro")
    public String intro(){
        return "/alarm/intro";
    }

}
