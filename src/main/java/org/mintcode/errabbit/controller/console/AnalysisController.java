package org.mintcode.errabbit.controller.console;

import org.apache.logging.log4j.Level;
import org.mintcode.errabbit.core.analysis.AggregationAnalyzer;
import org.mintcode.errabbit.core.analysis.request.LogAnalysisRequest;
import org.mintcode.errabbit.core.analysis.result.AnalysisResultSet;
import org.mintcode.errabbit.core.rabbit.managing.RabbitManagingService;
import org.mintcode.errabbit.core.rabbit.name.RabbitCache;
import org.mintcode.errabbit.model.Rabbit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Analysis is user query tool to handle data aggregation
 * Created by soleaf on 6/27/15.
 */
@Controller
@RequestMapping(value = "/anal")
public class AnalysisController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitManagingService rabbitManagingService;

    @Autowired
    private AggregationAnalyzer analyzer;

    @Autowired
    private RabbitCache rabbitCache;

    // Main UI
    @RequestMapping(value = "main")
    public ModelAndView main(Model model) {
        try {
            // Get Rabbit List
            model.addAttribute("rabbitGroups", rabbitManagingService.
                    getRabbitGroupWithRabbitSorted(rabbitManagingService.getRabbitsByGroup(rabbitCache.getRabbits())));
            return new ModelAndView("/anal/main");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("e", e);
            return new ModelAndView("/anal/main");
        }
    }

    // Aggregation (Ajax call)
    @RequestMapping(value = "aggregation", method = RequestMethod.POST)
    public ModelAndView aggregation(Model model,
                                    @RequestParam(value = "rabbit") String rabbit,
                                    @RequestParam(value = "level_trace", defaultValue = "false") Boolean trace,
                                    @RequestParam(value = "level_debug", defaultValue = "false") Boolean debug,
                                    @RequestParam(value = "level_info", defaultValue = "false") Boolean info,
                                    @RequestParam(value = "level_warn", defaultValue = "false") Boolean warn,
                                    @RequestParam(value = "level_error", defaultValue = "false") Boolean error,
                                    @RequestParam(value = "level_fatal", defaultValue = "false") Boolean fatal,
                                    @RequestParam(value = "date_begin", required = false) String date_begin,
                                    @RequestParam(value = "date_end", required = false) String date_end,
                                    @RequestParam(value = "groupBy", required = false) String groupBy
                                    )
    {
        try {

            // Make request model
            LogAnalysisRequest req = new LogAnalysisRequest();
            model.addAttribute("req", req);

            // fiilter rabbit
            if (rabbit != null && rabbit.length() > 0){
                Set<String> filterRabbits = new HashSet<>();
                filterRabbits.add(rabbit);
                req.setFilterRabbits(filterRabbits);
            }

            // filter by level
            if (trace)
                req.getFilterLevels().add(Level.TRACE.name());
            if (debug)
                req.getFilterLevels().add(Level.DEBUG.name());
            if (info)
                req.getFilterLevels().add(Level.INFO.name());
            if (warn)
                req.getFilterLevels().add(Level.WARN.name());
            if (error)
                req.getFilterLevels().add(Level.ERROR.name());
            if (fatal)
                req.getFilterLevels().add(Level.FATAL.name());

            // filter date
            if (StringUtils.hasLength(date_begin)){
                logger.trace("date_begin " + date_begin);
                req.setFilterBeginDate(Integer.parseInt(date_begin.replaceAll("-","")));
            }
            if (StringUtils.hasLength(date_end)){
                logger.trace("date_end " + date_end);
                req.setFilterEndDate(Integer.parseInt(date_end.replaceAll("-","")));
            }

            // group by
            if (StringUtils.hasLength(groupBy)){
                String[] groupByItems = groupBy.split(",");
                if (groupByItems != null && groupByItems.length > 0){
                    req.group = Arrays.asList(groupByItems);
                }
            }

            logger.trace("req : " + req);
            logger.trace("analyzer : " + analyzer);

            // Aggregation Query
            AnalysisResultSet result = analyzer.aggregation(req);
            if (result != null){
                model.addAttribute("result", result);
            }

            return new ModelAndView("/anal/result");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("e", e);
            return new ModelAndView("/anal/result");
        }
    }

}
