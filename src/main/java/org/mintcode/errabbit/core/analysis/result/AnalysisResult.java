package org.mintcode.errabbit.core.analysis.result;

import java.io.Serializable;

/**
 * Result of analysis(aggregation)
 * Implement by result presentation type
 * Created by soleaf on 6/28/15.
 */
public interface AnalysisResult extends Serializable {

    /**
     * GetResult
     * @return
     */
    public Object getResult();


}
