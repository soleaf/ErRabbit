package org.mintcode.errabbit.core.analysis.request;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.List;

/**
 * Created by soleaf on 8/30/15.
 */
public interface AnalysisRequest {

    public List<String> getColumns();

    public List<String> getGroup();

    public String getTargetCollection();

    public List<AggregationOperation> makeAggregationOp();

}
