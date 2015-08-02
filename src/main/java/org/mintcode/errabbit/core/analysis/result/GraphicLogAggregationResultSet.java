package org.mintcode.errabbit.core.analysis.result;

import org.mintcode.errabbit.core.analysis.FieldConverter;
import org.mintcode.errabbit.core.analysis.LogAggregationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Graphical analytic result set
 * Created by soleaf on 15. 8. 1..
 */
public class GraphicLogAggregationResultSet implements LogAggregationResultSet {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    GraphicLogAggregationResultItem root = new GraphicLogAggregationResultItem("root");

    public GraphicLogAggregationResultSet(LogAggregationRequest req, List<Map<String,Object>> list){
        for (Map<String,Object> row : list){
            GraphicLogAggregationResultItem superItem = null;
            for (String group : req.group){
                String field = "" + row.get(FieldConverter.converToFieldName(group));
                logger.trace("group" + group + " key + "  + FieldConverter.converToFieldName(group) +  " row " + row.keySet());
                GraphicLogAggregationResultItem target = findItem(superItem, field);
                superItem = target;
            }
            superItem.addCount((Integer) row.get("count"));
        }
        root.calcPercents();
        logger.debug(root.toString());
    }

    protected GraphicLogAggregationResultItem findItem(GraphicLogAggregationResultItem superItem, String field){

        GraphicLogAggregationResultItem parents;
        if (superItem == null){
            parents = root;
        }
        else{
            parents = superItem;
        }
        if (!parents.subItems.isEmpty()){
            for (GraphicLogAggregationResultItem item : parents.getSubItems()){
                if (item.getField().equals(field)){
                    return item;
                }
            }
        }

        GraphicLogAggregationResultItem item = new GraphicLogAggregationResultItem(field);
        parents.addSub(item);
        return item;
    }

    @Override
    public Object getResult() {
        return root;
    }
}
