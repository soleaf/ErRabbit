package org.mintcode.errabbit.controller.console;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.console.ReportPresentation;
import org.mintcode.errabbit.core.log.dao.LogLevelHourlyStatisticsRepository;
import org.mintcode.errabbit.core.rabbit.managing.RabbitManagingService;
import org.mintcode.errabbit.core.rabbit.name.RabbitCache;
import org.mintcode.errabbit.model.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mintcode.errabbit.core.log.dao.LogLevelDailyStatisticsRepository;
import org.mintcode.errabbit.core.log.dao.LogRepository;
import org.mintcode.errabbit.model.LogLevelDailyStatistics;
import org.mintcode.errabbit.model.Rabbit;
import org.mintcode.errabbit.model.Log;
import org.mintcode.errabbit.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Explorer logs for a rabbit
 * Created by soleaf on 2/16/15.
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private RabbitCache rabbitCache;

    @Autowired
    private RabbitManagingService rabbitManagingService;

    @Autowired
    private LogLevelDailyStatisticsRepository logLevelDailyStatisticsRepository;

    @Autowired
    private LogLevelHourlyStatisticsRepository logLevelHourlyStatisticsRepository;

    @Autowired
    private ReportPresentation reportPresentation;

    // Main UI
    @RequestMapping(value = "list")
    public ModelAndView list(Model model,
                             @RequestParam(value = "id", required = true) String id,
                             @RequestParam(required = false) Integer y,
                             @RequestParam(required = false) Integer m,
                             @RequestParam(required = false) Integer d
                             ) {
        try{
            Rabbit rabbit = rabbitCache.getRabbit(id);
            if (rabbit == null){
                model.addAttribute("e",new Exception("Can't find rabbit"));
                return new ModelAndView("/log/list");
            }

            // Mark read
            if (!rabbit.getRead()){
                rabbit.setRead(true);
                rabbitManagingService.saveRabbit(rabbit);
            }

            // Rabbit list
            model.addAttribute("groups", rabbitManagingService.
                    getRabbitGroupWithRabbitSorted(rabbitManagingService.getRabbitsByGroup(rabbitCache.getRabbits())));

            // Get first day of report
            LogLevelDailyStatistics firstDayStatistic = logLevelDailyStatisticsRepository.findByRabbitIdOnFirst(id);
            List<Integer> yearList = new ArrayList<Integer>();
            if (firstDayStatistic != null){

                // Today
                Date today = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(today);
                for (int listYear = firstDayStatistic.getYear(); listYear <= cal.get(Calendar.YEAR) ; listYear++){
                    yearList.add(listYear);
                }
                model.addAttribute("yearList",yearList);

                // filter by param
                if (y != null && m != null && d != null){
                    model.addAttribute("today_y", y);
                    model.addAttribute("today_m", m); // warn : jan.=1
                    model.addAttribute("today_d", d);
                    logger.trace("selected date y="+ y+ " m="+m +" d="+d);
                }
                else{
                    model.addAttribute("today_y", cal.get(Calendar.YEAR));
                    model.addAttribute("today_m", cal.get(Calendar.MONTH) + 1); // warn : jan.=1
                    model.addAttribute("today_d", cal.get(Calendar.DAY_OF_MONTH));
                }
            }

            model.addAttribute(rabbit);
            return new ModelAndView("/log/list");
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            // todo: make ErrorPage
            model.addAttribute("e",e);
            return new ModelAndView("/log/list");
        }
    }

    /**
     * Graph data (ajax)
     * @param id
     * @param year
     * @param month
     * @param day
     * @return
     */
    @RequestMapping(value = "day_graph")
    @ResponseBody
    public String dayGraph(@RequestParam(value = "id", required = true) String id,
                           @RequestParam(value = "y", required = true) Integer year,
                           @RequestParam(value = "m", required = true) Integer month, // warn : jan.=1){
                           @RequestParam(value = "d", required = true) Integer day){
        try{
            Graph graph = new Graph();
            graph.add(logLevelHourlyStatisticsRepository.findByRabbitIdAndYearAndMonthAndDay(id, year, month, day));
            return graph.getGraph();
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            return "[]";
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

            // LogLevel daily statistics
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

            return new ModelAndView("/log/day_data");
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            // todo: make ErrorPage
            model.addAttribute("e",e);
            return new ModelAndView("/log/day_data");
        }

    }

    /**
     * DayCell for UI
     * Calendar day cell class has LogLevelStatistics
     * It' using to show each day's log counts on UI
     */
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

    // log list data (ajax)
    @RequestMapping(value = "list_data")
    public ModelAndView reportListData(Model model,
                             @RequestParam(value = "id", required = true) String id,
                             @RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "size", required = false) Integer size,
                             @RequestParam(value = "y", required = true) String year,
                             @RequestParam(value = "m", required = true) String month,
                             @RequestParam(value = "d", required = true) String day,
                             @RequestParam(value = "level", required = false) String level,
                             @RequestParam(value = "class", required = false) String className
    ) {
        try{

            // target rabbit
            Rabbit rabbit = rabbitCache.getRabbit(id);
            if (rabbit == null){
                model.addAttribute("e",new Exception("Can't find rabbit"));
                return new ModelAndView("/log/list");
            }

            // paging and query params
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

            Integer loggingEventDateInt = Integer.parseInt(year + month + day);
            Sort sort = new Sort(Sort.Direction.DESC, "_id");

            // Filter options
            Page<Log> reportPage = null;
            if (level == null && className == null){
                reportPage = logRepository.findByLoggingEventDateIntAndRabbitId(
                        loggingEventDateInt
                        , id
                        , new PageRequest(page, size, sort));
            }
            else if (level !=null && className == null){
                reportPage = logRepository.findByLoggingEventDateIntAndRabbitIdAndLevel(
                        loggingEventDateInt
                        , id
                        , level
                        , new PageRequest(page, size, sort));
            }
            else if (level ==null && className != null){
                reportPage = logRepository.findByLoggingEventDateIntAndRabbitIdAndClassName(
                        loggingEventDateInt
                        , id
                        , className
                        , new PageRequest(page, size, sort));
            }
            else if (level !=null && className != null){
                reportPage = logRepository.findByLoggingEventDateIntAndRabbitIdAndLevelAndClassName(
                        loggingEventDateInt
                        , id
                        , level
                        , className
                        , new PageRequest(page, size, sort));
            }

            logger.trace("level="+level + ", className="+className);
            logger.trace("Result of retrieve reportPage > " + reportPage.getContent().size());
            model.addAttribute("reports", reportPage);
            model.addAttribute("format", new SimpleDateFormat("HH:mm:ss:SSS")); // format for UI
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            model.addAttribute("e",e);
        }
        return new ModelAndView("/log/list_data");

    }

    // Log popover data (ajax)
    @RequestMapping(value = "popover_data")
    public ModelAndView reportListData(Model model,
                                       @RequestParam(value = "id", required = true) String id

    ) {
        try{
            Log log = logRepository.findOne(new ObjectId(id));
            Rabbit rabbit = rabbitCache.getRabbit(log.getRabbitId());
            model.addAttribute("graphs", reportPresentation.makeTraceGraph(rabbit.getBasePackage(), log));
            model.addAttribute("log", log);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            model.addAttribute("e",e);
        }
        return new ModelAndView("/log/popover_data");
    }


}
