package org.mintcode.errabbit.core.analysis;

import java.util.List;
import java.util.Map;

/**
 * Created by soleaf on 7/12/15.
 */
public class LogAggregationResultTable implements LogAggregationResultSet {

    List<Map<String,Object>> list;

    public LogAggregationResultTable(){

    }

    public LogAggregationResultTable(List<Map<String,Object>> list){
        this.list = list;
    }

    public List<Map<String,Object>> getResult(){
        return list;
    }
}
