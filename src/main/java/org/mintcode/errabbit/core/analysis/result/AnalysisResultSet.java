package org.mintcode.errabbit.core.analysis.result;

import org.mintcode.errabbit.core.analysis.request.AnalysisRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * One or more results types for one result.
 * Created by soleaf on 15. 8. 2..
 */
public class AnalysisResultSet implements Serializable {

    public static final String TABLE = "table";
    public static final String GRAPHIC = "graphic";

    Map<String,AnalysisResult> results = new HashMap<>();

    @Transient
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public AnalysisResultSet(){

    }

    /**
     * Set result
     * @param results
     */
    public void setResults(Map<String, AnalysisResult> results) {
        this.results = results;
    }

    /**
     * initation with result
     * @param req
     * @param result
     */
    public AnalysisResultSet(AnalysisRequest req, List<Map<String, Object>> result){
        logger.trace("result > " + result);
        results.put(TABLE, new TableLogAnalysisResult(req, result));
        results.put(GRAPHIC, new GraphicLogAnalysisResult(req, result));
    }

    /**
     * Get result by type
     * @param type
     * @return
     */
    public AnalysisResult get(String type){
        return results.get(type);
    }

    /**
     * Check is empty result
     * @return
     */
    public boolean isEmpty(){
        return results.isEmpty();
    }
}
