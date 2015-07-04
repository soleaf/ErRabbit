package org.mintcode.errabbit.core.analysis;

import org.mintcode.errabbit.model.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public LogAggregationResult aggregation(LogAggregationRequest request){

        List op = makeAggregationOpFromReq(request);
        logger.trace("op : " + op);

        AggregationResults result = mongoTemplate.aggregate(Aggregation.newAggregation(op),
                "report", mapType.getClass());

        List resultList  = result.getMappedResults();
        logger.warn("result : " + resultList);
        return null;
    }

    /**
     * Make MongoAggregationOperations from LogAggregationRequest
     * @param req
     * @return
     */
    public List makeAggregationOpFromReq(LogAggregationRequest req){

        List op = new ArrayList();

        // Filter : RabbitId
        if (req.filterRabbit != null){
            op.add(new MatchOperation(Criteria.where("rabbit").is(req.filterRabbit)));
        }
        // Filter : Levels
        if (req.filterLevels.size() > 0){
            Criteria orCriteria = null;
            for (String level : req.filterLevels){
                if (orCriteria == null)
                    orCriteria = Criteria.where("loggingEvent.level").is(level);
                else
                    orCriteria.orOperator(Criteria.where("loggingEvent.level").is(level));
            }
            op.add(new MatchOperation(orCriteria));
        }
        // Filter Date
        if (req.filterBeginDate != null){
            op.add(new MatchOperation(Criteria.where("loggingEventDateInt").gte(req.filterBeginDate)));
        }
        if (req.filterEndDate != null){
            op.add(new MatchOperation(Criteria.where("loggingEventDateInt").lte(req.filterEndDate)));
        }

        // Group by
        if (req.group != null){
            op.add(new GroupOperation(Fields.fields((String[]) req.group.toArray())).count().as("count"));
        }

        return  op;
    }

}
