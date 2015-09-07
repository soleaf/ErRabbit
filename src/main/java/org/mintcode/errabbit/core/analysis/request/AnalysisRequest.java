package org.mintcode.errabbit.core.analysis.request;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.io.Serializable;
import java.util.List;

/**
 * Created by soleaf on 8/30/15.
 */
public interface AnalysisRequest extends Serializable {

    public List<String> getColumns();

    public List<String> getGroup();

    public String getTargetCollection();

    public List<AggregationOperation> makeAggregationOp();

}
