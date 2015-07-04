package org.mintcode.errabbit.core.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by soleaf on 6/28/15.
 */
public class LogAggregationRequest {

    // Filter (null = All)
    public String filterRabbit;
    public Set<String> filterLevels = new HashSet<>();
    public Integer filterBeginDate; // ex 20150601
    public Integer filterEndDate; // ex 20150601

    // Group By
    public List<String> group;

    @Override
    public String toString() {
        return "LogAggregationRequest{" +
                "filterRabbit='" + filterRabbit + '\'' +
                ", filterLevels=" + filterLevels +
                ", filterBeginDate=" + filterBeginDate +
                ", filterEndDate=" + filterEndDate +
                ", group=" + group +
                '}';
    }
}
