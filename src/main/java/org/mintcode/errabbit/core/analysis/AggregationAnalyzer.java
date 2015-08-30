package org.mintcode.errabbit.core.analysis;

import org.mintcode.errabbit.core.analysis.request.LogAnalysisRequest;
import org.mintcode.errabbit.core.analysis.result.AnalysisResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by soleaf on 8/30/15.
 */
@Service
public class AggregationAnalyzer {

    @Autowired
    MongoTemplate mongoTemplate;

    private final HashMap<String,Object> mapType = new HashMap<String,Object>();

    public AnalysisResultSet aggregation(LogAnalysisRequest req) {
        AggregationResults result = mongoTemplate.aggregate(Aggregation.newAggregation(req.makeAggregationOp()),
                req.getTargetCollection(), mapType.getClass());
        return new AnalysisResultSet(req, result.getMappedResults());
    }

}
