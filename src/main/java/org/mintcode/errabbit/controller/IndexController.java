package org.mintcode.errabbit.controller;

import org.mintcode.errabbit.core.log.dao.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @Autowired
    LogRepository logRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showIndex() {
        return "redirect:/rabbit/list";
    }

}
