package org.mintcode.errabbit.controller.console;

import org.apache.logging.log4j.Level;
import org.mintcode.errabbit.core.analysis.AggregationAnalysis;
import org.mintcode.errabbit.core.analysis.LogAggregationRequest;
import org.mintcode.errabbit.core.analysis.LogAggregationResult;
import org.mintcode.errabbit.core.rabbit.managing.RabbitManagingService;
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
import java.util.List;

/**
 * Created by soleaf on 6/27/15.
 */
@Controller
@RequestMapping(value = "/anal")
public class AnalysisController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitManagingService rabbitManagingService;

    @Autowired
    private AggregationAnalysis analysis;

    // main
    @RequestMapping(value = "main")
    public ModelAndView list(Model model) {
        try {
            // Get Rabbit List
            List<Rabbit> rabbitList = rabbitManagingService.getRabbits();
            model.addAttribute(rabbitList);
            return new ModelAndView("/anal/main");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            // todo: make ErrorPage
            model.addAttribute("e", e);
            return new ModelAndView("/anal/main");
        }
    }

    // Aggregation
    @RequestMapping(value = "aggregation", method = RequestMethod.POST)
    public ModelAndView aggregation(Model model,
                                    @RequestParam(value = "rabbit") String rabbit,
                                    @RequestParam(value = "level_trace", defaultValue = "false") Boolean trace,
                                    @RequestParam(value = "level_debug", defaultValue = "false") Boolean debug,
                                    @RequestParam(value = "level_info", defaultValue = "false") Boolean info,
                                    @RequestParam(value = "level_warn", defaultValue = "false") Boolean warn,
                                    @RequestParam(value = "level_error", defaultValue = "false") Boolean error,
                                    @RequestParam(value = "level_fatal", defaultValue = "false") Boolean fatal,
                                    @RequestParam(value = "date_begin") Integer date_begin,
                                    @RequestParam(value = "date_end") Integer date_end,
                                    @RequestParam(value = "groupBy") String groupBy
                                    )
    {
        try {

            LogAggregationRequest req = new LogAggregationRequest();

            if (StringUtils.hasLength(rabbit)){
                req.filterRabbit = rabbit;
            }

            if (trace)
                req.filterLevels.add(Level.TRACE.name());
            if (debug)
                req.filterLevels.add(Level.DEBUG.name());
            if (info)
                req.filterLevels.add(Level.INFO.name());
            if (warn)
                req.filterLevels.add(Level.WARN.name());
            if (error)
                req.filterLevels.add(Level.ERROR.name());
            if (fatal)
                req.filterLevels.add(Level.FATAL.name());
            if (date_begin != null){
                req.filterBeginDate = date_begin;
            }
            if (date_end != null){
                req.filterEndDate = date_end;
            }

            if (StringUtils.hasLength(groupBy)){
                String[] groupByItems = groupBy.split(",");
                if (groupByItems != null && groupByItems.length > 0){
                    req.group.addAll(Arrays.asList(groupByItems));
                }
            }

            LogAggregationResult result = analysis.aggregation(req);
            if (result != null){
                model.addAttribute(result);
            }

            logger.trace("req : " + req);

            // Aggregation query
            return new ModelAndView("/anal/main");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            // todo: make ErrorPage
            model.addAttribute("e", e);
            return new ModelAndView("/anal/main");
        }
    }

}
