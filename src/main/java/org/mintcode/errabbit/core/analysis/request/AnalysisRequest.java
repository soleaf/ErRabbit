package org.mintcode.errabbit.core.analysis.request;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.io.Serializable;
import java.util.List;

/**
 * Analysis Request
 * Implement by target collection
 * Created by soleaf on 8/30/15.
 */
public interface AnalysisRequest extends Serializable {

    /**
     * Get group by element removed prefix.
     * @return
     */
    public List<String> getColumns();

    /**
     * Get group by elements
     * @return
     */
    public List<String> getGroup();

    /**
     * Get target collection name
     * @return
     */
    public String getTargetCollection();

    /**
     * Generate MongoDB AggregationOperation model to aggregate
     * @return
     */
    public List<AggregationOperation> makeAggregationOp();

}
