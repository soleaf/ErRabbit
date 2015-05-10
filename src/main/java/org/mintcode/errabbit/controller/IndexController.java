package org.mintcode.errabbit.controller;

import org.mintcode.errabbit.core.report.dao.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    ReportRepository reportRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showIndex() {
        return "redirect:/rabbit/list";
    }

}
