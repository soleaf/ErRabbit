package org.mintcode.errabbit.core.analysis.result;

import org.mintcode.errabbit.core.analysis.request.LogAnalysisRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soleaf on 15. 8. 2..
 */
public class AnalysisResultSet {
    public static final String TABLE = "table";
    public static final String GRAPHIC = "graphic";
    Map<String,AnalysisResult> results = new HashMap<>();

    public AnalysisResultSet(LogAnalysisRequest req, List<Map<String, Object>> result){
        results.put(TABLE, new TableLogAnalysisResult(result));
        results.put(GRAPHIC, new GraphicLogAnalysisResult(req, result));
    }

    public AnalysisResult get(String type){
        return results.get(type);
    }

    public boolean isEmpty(){
        return results.isEmpty();
    }
}
