package org.mintcode.errabbit.core.analysis.result;

import java.util.List;
import java.util.Map;

/**
 * Created by soleaf on 7/12/15.
 */
public class TableLogAggregationResultSet implements LogAggregationResultSet {

    List<Map<String,Object>> list;

    public TableLogAggregationResultSet(){

    }

    public TableLogAggregationResultSet(List<Map<String, Object>> list){
        this.list = list;
    }

    public List<Map<String,Object>> getResult(){
        return list;
    }
}
