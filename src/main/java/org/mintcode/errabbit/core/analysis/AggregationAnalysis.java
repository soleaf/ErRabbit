package org.mintcode.errabbit.core.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregation works for log
 * Created by soleaf on 6/28/15.
 */
@Service
public class AggregationAnalysis {

    @Autowired
    MongoTemplate mongoTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HashMap<String,Object> mapType = new HashMap<String,Object>();

    public LogAggregationResultSet aggregation(LogAggregationRequest request){

        List op = makeAggregationOpFromReq(request);
        logger.trace("op : " + op);

        AggregationResults result = mongoTemplate.aggregate(Aggregation.newAggregation(op),
                "reports", mapType.getClass());

        List resultList = result.getMappedResults();
        return makeResult(request, resultList);
    }

    /**
     * Make MongoAggregationOperations from LogAggregationRequest
     * @param req
     * @return
     */
    private List<AggregationOperation> makeAggregationOpFromReq(LogAggregationRequest req){

        List<AggregationOperation> op = new ArrayList<>();

        // Filter : RabbitId
        if (req.getFilterRabbit() != null){
            op.add(new MatchOperation(Criteria.where("rabbit").is(req.getFilterRabbit())));
        }

        // Filter : Levels
        if (req.getFilterLevels().size() > 0){
            logger.trace("levels " + req.getFilterLevels());
//            Criteria levelCriteria = null;
//            Criteria[] orCriteria = null;
//            if (req.getFilterLevels().size() > 1){
//                orCriteria = new Criteria[req.getFilterLevels().size()-1];
//            }
//
//            int i = 0;
//            for (String level : req.getFilterLevels()){
//                if (levelCriteria == null)
//                    levelCriteria = Criteria.where("loggingEvent.level").is(level);
//                else{
//                    orCriteria[i] = Criteria.where("loggingEvent.level").is(level);
//                    i++;
//                }
//            }
//
//            if (orCriteria != null){
//                levelCriteria = levelCriteria.orOperator(orCriteria);
//                logger.trace("append orOperator");
//            }

            op.add(new MatchOperation(Criteria.where("loggingEvent.level").in(req.getFilterLevels())));
        }

        // Filter : Date
        Criteria dateCriteria = null;
        if (req.getFilterBeginDate() != null){
            dateCriteria = Criteria.where("loggingEventDateInt").gte(req.getFilterBeginDate());
        }
        if (req.getFilterEndDate() != null){
            if (dateCriteria == null){
                dateCriteria = Criteria.where("loggingEventDateInt").lte(req.getFilterEndDate());
            }
            else{
                dateCriteria = dateCriteria.andOperator(Criteria.where("loggingEventDateInt").lte(req.getFilterEndDate()));
            }
        }
        if (dateCriteria != null){
            op.add(new MatchOperation(dateCriteria));
        }

        // Group by
        if (req.group != null){
            op.add(new GroupOperation(Fields.fields((String[]) req.group.toArray())).count().as("count"));
        }

        return op;
    }

    /**
     * Make Result Obj
     * @param req
     * @param result
     * @return
     */
    private LogAggregationResultSet makeResult(LogAggregationRequest req, List<Map<String,Object>> result){
        return new LogAggregationResultTable(result);
    }

}
