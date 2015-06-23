package org.mintcode.errabbit.core.console;

import org.mintcode.errabbit.model.ErStackTraceElement;
import org.mintcode.errabbit.model.Rabbit;
import org.mintcode.errabbit.model.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soleaf on 6/7/15.
 */
@Service
public class ReportPresentation {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Generate TraceGraph Many
     * @param reportPage
     * @return
     */
    public Map<Report,List<StackTraceGraph>> makeTraceGraph(String basePackage, Page<Report> reportPage){
        Map<Report,List<StackTraceGraph>> graphs = new HashMap<Report, List<StackTraceGraph>>();
        for (Report report : reportPage.getContent()){
            graphs.put(report, makeTraceGraph(basePackage, report));
        }
        return graphs;
    }

    /**
     * Generate TraceGraph One
     * @param report
     * @return
     */
    public List<StackTraceGraph> makeTraceGraph(String basePackage, Report report){

        try{
            if (report.getLoggingEvent().getThrowableInfo() == null){
                return null;
            }

            List<StackTraceGraph> graphs = new ArrayList<StackTraceGraph>();

            StackTraceGraph lastGraph = new StackTraceGraph(basePackage);
            graphs.add(lastGraph);


            for (ErStackTraceElement element :
                    report.getLoggingEvent().getThrowableInfo().getThrowable().getStackTraceElements()){
                if (lastGraph.getClassName() != null &&
                        !lastGraph.getClassName().equals(element.getDeclaringClass())){
                    lastGraph = new StackTraceGraph(basePackage);
                    graphs.add(lastGraph);
                }
                lastGraph.addElement(element);

            }
            return graphs;
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }

    }

}
