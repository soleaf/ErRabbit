package org.mintcode.errabbit.core.console;

import org.mintcode.errabbit.model.ErStackTraceElement;
import org.mintcode.errabbit.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Report presentation is graphic log presentation model.
 * It make Log's stack trace info UI usable model.
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
    public Map<Log,List<StackTraceGraph>> makeTraceGraph(String basePackage, Page<Log> reportPage){
        Map<Log,List<StackTraceGraph>> graphs = new HashMap<Log, List<StackTraceGraph>>();
        for (Log log : reportPage.getContent()){
            graphs.put(log, makeTraceGraph(basePackage, log));
        }
        return graphs;
    }

    /**
     * Generate TraceGraph One
     * @param log
     * @return
     */
    public List<StackTraceGraph> makeTraceGraph(String basePackage, Log log){

        try{
            if (log.getLoggingEvent().getThrowableInfo() == null){
                return null;
            }

            List<StackTraceGraph> graphs = new ArrayList<StackTraceGraph>();

            StackTraceGraph lastGraph = new StackTraceGraph(basePackage);
            graphs.add(lastGraph);

            for (ErStackTraceElement element :
                    log.getLoggingEvent().getThrowableInfo().getThrowable().getStackTraceElements()){

                // Make Class
                if (lastGraph.getClassName() != null
                        && !lastGraph.getClassName().equals(element.getDeclaringClass())){
                    lastGraph = new StackTraceGraph(basePackage);
                    graphs.add(lastGraph);
                }

                // Add Method Call
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
