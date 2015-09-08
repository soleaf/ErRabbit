package org.mintcode.errabbit.core.report;

import org.apache.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mintcode.errabbit.core.analysis.AggregationAnalyzer;
import org.mintcode.errabbit.core.analysis.request.LogAnalysisRequest;
import org.mintcode.errabbit.core.analysis.request.LogLevelAnalysisRequest;
import org.mintcode.errabbit.core.analysis.result.AnalysisResultSet;
import org.mintcode.errabbit.core.report.dao.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

/**
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

    public boolean generate(ReportDescription description, Date date){
        try{
            Integer targetDateInt = Integer.parseInt(format.format(date));
            AnalysisResultSet logAggregation = makeLogAnal(description, targetDateInt);
            AnalysisResultSet logLevelAggregation = makeLogLevelAnal(description, targetDateInt);

            Report report = new Report();
            report.setSendTime(new Date());
            report.setLogReport(logAggregation);
            report.setLogLevelReport(logLevelAggregation);

            logger.debug("generated > " + report);
            reportRepository.save(report);
            return true;
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    private AnalysisResultSet makeLogAnal(ReportDescription description, Integer targetDateInt){
        // Make logLevel count table group by rabbit.
        Set<String> levelSet = new HashSet<>();
        levelSet.add(Level.ERROR.toString());
        levelSet.add(Level.WARN.toString());
        levelSet.add(Level.FATAL.toString());

        List<String> group = new ArrayList<>();
        group.add("rabbitId");
        group.add("loggingEvent.categoryName");
        group.add("loggingEvent.loggingEvent.categoryName");

        LogAnalysisRequest req = new LogAnalysisRequest();
        req.setFilterRabbits(description.getTargets());
        req.setFilterBeginDate(targetDateInt);
        req.setFilterEndDate(targetDateInt);
        req.setFilterLevels(levelSet);
        req.setGroup(group);

        logger.trace("logAnalReq > " +req);

        return analyzer.aggregation(req);
    }

    private AnalysisResultSet makeLogLevelAnal(ReportDescription description, Integer targetDateInt){
        // Make logLevel count table group by rabbit.
        List<String> group = new ArrayList<>();
        group.add("rabbitId");
        group.add("loggingEvent.categoryName");
        group.add("loggingEvent.loggingEvent.categoryName");

        LogLevelAnalysisRequest req = new LogLevelAnalysisRequest();
        req.setFilterRabbits(description.getTargets());
        req.setFilterBeginDate(targetDateInt);
        req.setFilterEndDate(targetDateInt);
        req.setGroup(group);
        return analyzer.aggregation(req);
    }

}
