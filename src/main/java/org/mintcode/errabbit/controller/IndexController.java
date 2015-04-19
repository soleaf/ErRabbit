package org.mintcode.errabbit.controller;

import org.mintcode.errabbit.core.report.dao.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SuppressWarnings("UnusedDeclaration")
public class IndexController {

    @Autowired
    ReportRepository reportRepository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public String showIndex() {

//        List<ExceptionLog> logs = reportRepository.findReports();

//        return logs.toString();
        return "";
    }

    @RequestMapping(value = "/hello")
    public String hello(){
        return "test";
    }

}
