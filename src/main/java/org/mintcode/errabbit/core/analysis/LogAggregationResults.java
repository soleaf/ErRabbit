package org.mintcode.errabbit.core.analysis;

import org.mintcode.errabbit.core.analysis.result.GraphicLogAggregationResultSet;
import org.mintcode.errabbit.core.analysis.result.LogAggregationResultSet;
import org.mintcode.errabbit.core.analysis.result.TableLogAggregationResultSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soleaf on 15. 8. 2..
 */
public class LogAggregationResults {
    public static final String TABLE = "table";
    public static final String GRAPHIC = "graphic";
    Map<String,LogAggregationResultSet> results = new HashMap<>();

    public LogAggregationResults(LogAggregationRequest req, List<Map<String,Object>> result){
        results.put(TABLE, new TableLogAggregationResultSet(result));
        results.put(GRAPHIC, new GraphicLogAggregationResultSet(req, result));
    }

    public LogAggregationResultSet get(String type){
        return results.get(type);
    }
}
