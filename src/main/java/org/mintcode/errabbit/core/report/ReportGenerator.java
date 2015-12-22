package org.mintcode.errabbit.core.report;

import org.apache.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mintcode.errabbit.core.analysis.AggregationAnalyzer;
import org.mintcode.errabbit.core.analysis.request.LogAnalysisRequest;
import org.mintcode.errabbit.core.analysis.result.AnalysisResultSet;
import org.mintcode.errabbit.core.report.dao.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ReportGenerator
 * Created by soleaf on 15. 8. 27..
 */
@Service
public class ReportGenerator {

    Logger logger = LogManager.getLogger(getClass());

    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    AggregationAnalyzer analyzer;

    @Autowired
    ReportRepository reportRepository;

    /**
     * Generate report using description and target date
     * @param description
     * @param date target date
     * @return
     */
    public boolean generate(ReportDescription description, Date date){
        try{

            // Get log aggregation analysis
            Integer targetDateInt = Integer.parseInt(format.format(date));
            AnalysisResultSet logAggregation = makeLogAnal(description, targetDateInt);

            Report report = new Report();
            report.setSendTime(new Date());
            report.setTargetDate(date);
            report.setLogReport(logAggregation);
            report.setTargets(description.getTargets());
            logger.debug("generated > " + report);
            reportRepository.save(report);
            return true;
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Make aggregation by log
     * @param description
     * @param targetDateInt
     * @return
     */
    private AnalysisResultSet makeLogAnal(ReportDescription description, Integer targetDateInt){
        // Make logLevel count table group by rabbit.
        Set<String> levelSet = new HashSet<>();
        levelSet.add(Level.ERROR.toString());
        levelSet.add(Level.WARN.toString());
        levelSet.add(Level.FATAL.toString());

        List<String> group = new ArrayList<>();
        group.add("rabbitId");
        group.add("loggingEvent.categoryName");
        group.add("loggingEvent.level");

        LogAnalysisRequest req = new LogAnalysisRequest();
        req.setFilterRabbits(description.getTargets());
        req.setFilterBeginDate(targetDateInt);
        req.setFilterEndDate(targetDateInt);
        req.setFilterLevels(levelSet);
        req.setGroup(group);

        logger.trace("logAnalReq > " + req);

        return analyzer.aggregation(req);
    }

}
