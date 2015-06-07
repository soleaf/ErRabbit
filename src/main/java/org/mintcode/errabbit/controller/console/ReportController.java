package org.mintcode.errabbit.controller.console;

import org.mintcode.errabbit.core.console.ReportPresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mintcode.errabbit.core.rabbit.dao.RabbitRepository;
import org.mintcode.errabbit.core.report.dao.LogLevelDailyStatisticsRepository;
import org.mintcode.errabbit.core.report.dao.ReportRepository;
import org.mintcode.errabbit.model.ErStackTraceElement;
import org.mintcode.errabbit.model.LogLevelDailyStatistics;
import org.mintcode.errabbit.model.Rabbit;
import org.mintcode.errabbit.model.Report;
import org.mintcode.errabbit.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by soleaf on 2/16/15.
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private RabbitRepository rabbitRepository;

    @Autowired
    private LogLevelDailyStatisticsRepository logLevelDailyStatisticsRepository;

    @Autowired
    private ReportPresentation reportPresentation;

    // List for report
    @RequestMapping(value = "list")
    public ModelAndView list(Model model,
                             @RequestParam(value = "id", required = true) String id) {
        try{
            Rabbit rabbit = rabbitRepository.findById(id);
            if (rabbit == null){
                model.addAttribute("e",new Exception("Can't find rabbit"));
                return new ModelAndView("/report/list");
            }

            // Mark read
            if (!rabbit.getRead()){
                rabbit.setRead(true);
                rabbitRepository.save(rabbit);
            }

            // Get first day of report
            LogLevelDailyStatistics firstDayStatistic = logLevelDailyStatisticsRepository.findByRabbitIdOnFirst(id);
            List<Integer> yearList = new ArrayList<Integer>();
            if (firstDayStatistic != null){

                // Today
                Date today = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(today);

                for (int y = firstDayStatistic.getYear(); y <= cal.get(Calendar.YEAR) ; y++){
                    yearList.add(y);
                }
                model.addAttribute("yearList",yearList);
                model.addAttribute("today_y", cal.get(Calendar.YEAR));
                model.addAttribute("today_m", cal.get(Calendar.MONTH) +1); // warn : jan.=1
                model.addAttribute("today_d", cal.get(Calendar.DAY_OF_MONTH));
            }

            model.addAttribute(rabbit);
            return new ModelAndView("/report/list");
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            // todo: make ErrorPage
            model.addAttribute("e",e);
            return new ModelAndView("/report/list");
        }
    }

    // AJAX Days of month List Data
    // Make Statics for each day on month
    @RequestMapping(value = "list_days")
    public ModelAndView dayStaticListData(Model model,
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "y", required = true) Integer year,
            @RequestParam(value = "m", required = true) Integer month, // warn : jan.=1
            @RequestParam(value = "s", required = false) Long selectedDay
    ){
        try{

            // Make Calendar with blank cell
            int lastDayOfMonth = DateUtil.getLastDayOfMonth(year, month);
            List<DayCell> cellList = new ArrayList<DayCell>();
            for (int i=0 ; i < lastDayOfMonth; i++){
                cellList.add(new DayCell(i+1));
            }

            List<LogLevelDailyStatistics> logLevelDailyStatisticses =
                    logLevelDailyStatisticsRepository.findByRabbitIdAndYearAndMonth(id, year, month);

            // Set statistic to cell
            for (LogLevelDailyStatistics statistics : logLevelDailyStatisticses){
                DayCell cell = cellList.get(statistics.getDay() - 1);
                cell.setStatistics(statistics);
            }
            model.addAttribute(cellList);

            if (selectedDay != null){
                model.addAttribute("selectedDay", selectedDay);
            }

            return new ModelAndView("/report/day_data");
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            // todo: make ErrorPage
            model.addAttribute("e",e);
            return new ModelAndView("/report/day_data");
        }

    }

    public class DayCell {
        private Integer dayOfMonth;
        private LogLevelDailyStatistics statistics;
        public DayCell(Integer dayOfMonth){
            this.dayOfMonth = dayOfMonth;
        }

        public Integer getDayOfMonth() {
            return dayOfMonth;
        }

        public void setDayOfMonth(Integer dayOfMonth) {
            this.dayOfMonth = dayOfMonth;
        }

        public LogLevelDailyStatistics getStatistics() {
            return statistics;
        }

        public void setStatistics(LogLevelDailyStatistics statistics) {
            this.statistics = statistics;
        }
    }

    // AJAX Report List DATA API for report
    @RequestMapping(value = "list_data")
    public ModelAndView reportListData(Model model,
                             @RequestParam(value = "id", required = true) String id,
                             @RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "size", required = false) Integer size,
                             @RequestParam(value = "y", required = true) String year,
                             @RequestParam(value = "m", required = true) String month,
                             @RequestParam(value = "d", required = true) String day
    ) {
        try{

            Rabbit rabbit = rabbitRepository.findById(id);
            if (rabbit == null){
                model.addAttribute("e",new Exception("Can't find rabbit"));
                return new ModelAndView("/report/list");
            }

            if (page == null){
                page = 0;
            }
            if (size == null){
                size = 500;
            }
            if (month.length() < 2){
                month = "0" + month;
            }
            if (day.length() < 2){
                day = "0" + day;
            }

            logger.trace(String.format("Request of retrieve reportPage page:%d size:%d",page, size));

            Integer loggingEventDateInt = Integer.parseInt(year + month + day);
            Sort sort = new Sort(Sort.Direction.DESC, "_id");

            Page<Report> reportPage
                    = reportRepository.findByRabbitIdAndLoggingEventDateInt(id
                    , loggingEventDateInt
                    , new PageRequest(page, size, sort));

            logger.trace("Result of retrieve reportPage > " + reportPage.getContent().size());
            model.addAttribute("reports", reportPage);
            model.addAttribute("graphs", reportPresentation.makeTraceGraph(rabbit.getBasePackage(), reportPage));
            model.addAttribute("format", new SimpleDateFormat("HH:mm:ss:SSS"));
            return new ModelAndView("/report/list_data");

        }
        catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            // todo: make ErrorPage
            model.addAttribute("e",e);
            return new ModelAndView("/report/list_data");
        }

    }



}
