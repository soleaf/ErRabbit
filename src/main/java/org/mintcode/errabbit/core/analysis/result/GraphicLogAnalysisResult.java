package org.mintcode.errabbit.core.analysis.result;

import org.mintcode.errabbit.core.analysis.FieldConverter;
import org.mintcode.errabbit.core.analysis.request.AnalysisRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import java.util.*;

/**
 * Analysis result type Graphics
 * Tree structure
 * Rank model by level and count
 * Created by soleaf on 15. 8. 1..
 */
public class GraphicLogAnalysisResult implements AnalysisResult {

    @Transient
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<GraphicLogAnalysisErrorRankItem> ranks;

    private GraphicLogAnalysisResultItem root;

    public GraphicLogAnalysisResult(){

    }

    public GraphicLogAnalysisResult(AnalysisRequest req, List<Map<String, Object>> list){

        // Make tree
        makeTree(req, list);

        // Make rank
        makeRank(req, list);

    }

    private void makeRank(AnalysisRequest req, List<Map<String, Object>> list){
        if (!req.getGroup().contains("rabbitId") ||
                !req.getGroup().contains("loggingEvent.categoryName") ||
                !req.getGroup().contains("loggingEvent.level")){
            logger.info("can't make rank for error insufficient data(group) : " + req.getGroup());
            return;
        }

        ranks = new ArrayList<>();
        for (Map<String,Object> row : list){
            String rabbit = (String) row.get(FieldConverter.toFieldName("rabbitId"));
            String className = (String) row.get(FieldConverter.toFieldName("loggingEvent.categoryName"));
            Integer count = (Integer) row.get("count");
            String level = (String) row.get(FieldConverter.toFieldName("loggingEvent.level"));
            GraphicLogAnalysisErrorRankItem errorRankItem = new GraphicLogAnalysisErrorRankItem();
            errorRankItem.setLevel(level);
            errorRankItem.setRabbitId(rabbit);
            errorRankItem.setClassName(className);
            errorRankItem.setCount(count);
            ranks.add(errorRankItem);
        }

        // sort
        Collections.sort(ranks, Collections.reverseOrder());
        logger.trace("Made ranks \n" + ranks);

    }

    private void makeTree(AnalysisRequest req, List<Map<String, Object>> list){
        root = new GraphicLogAnalysisResultItem("root");
        for (Map<String,Object> row : list){
            GraphicLogAnalysisResultItem superItem = null;
            for (String group : req.getGroup()){
                String field = "" + row.get(FieldConverter.toFieldName(group));
                logger.trace("group" + group + " key + "  + FieldConverter.toFieldName(group) +  " row " + row.keySet());
                GraphicLogAnalysisResultItem target = findItem(superItem, field);
                superItem = target;
            }
            superItem.addCount((Integer) row.get("count"));
        }
        root.calcPercents();
        logger.debug(root.toString());
    }

    protected GraphicLogAnalysisResultItem findItem(GraphicLogAnalysisResultItem superItem, String field){

        GraphicLogAnalysisResultItem parents;
        if (superItem == null){
            parents = root;
        }
        else{
            parents = superItem;
        }
        if (parents.subItems != null && !parents.subItems.isEmpty()){
            for (GraphicLogAnalysisResultItem item : parents.getSubItems()){
                if (item.getField().equals(field)){
                    return item;
                }
            }
        }

        GraphicLogAnalysisResultItem item = new GraphicLogAnalysisResultItem(field);
        parents.addSub(item);
        return item;
    }

    public void setRoot(GraphicLogAnalysisResultItem root) {
        this.root = root;
    }

    public GraphicLogAnalysisResultItem getRoot() {
        return root;
    }

    public List<GraphicLogAnalysisErrorRankItem> getRanks() {
        return ranks;
    }

    public List<GraphicLogAnalysisErrorRankItem> getRanksLimit(Integer limit) {
        if (ranks == null || ranks.isEmpty() || ranks.size() == 0){
            return null;
        }
        List<GraphicLogAnalysisErrorRankItem> tempRanks = new ArrayList<>();
        for (Integer i=0; i < limit && i < ranks.size(); i++){
            tempRanks.add(ranks.get(i));
        }

        return tempRanks;
    }



    public void setRanks(List<GraphicLogAnalysisErrorRankItem> ranks) {
        this.ranks = ranks;
    }

    @Override
    public Object getResult() {
        return root;
    }
}
