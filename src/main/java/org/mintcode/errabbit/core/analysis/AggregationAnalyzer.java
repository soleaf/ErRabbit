package org.mintcode.errabbit.core.analysis;


import org.mintcode.errabbit.core.analysis.request.AnalysisRequest;
import org.mintcode.errabbit.core.analysis.result.AnalysisResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AnalysisResultSet aggregation(AnalysisRequest req) {
        logger.trace("req > " + req.makeAggregationOp());
        AggregationResults result = mongoTemplate.aggregate(Aggregation.newAggregation(req.makeAggregationOp()),
                req.getTargetCollection(), mapType.getClass());
        logger.trace("result >" + result.getMappedResults());
        return new AnalysisResultSet(req, result.getMappedResults());
    }

}
