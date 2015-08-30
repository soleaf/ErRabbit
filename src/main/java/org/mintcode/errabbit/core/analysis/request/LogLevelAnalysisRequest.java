package org.mintcode.errabbit.core.analysis.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by soleaf on 8/30/15.
 */
public class LogLevelAnalysisRequest implements AnalysisRequest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Filter (null = All)
    private Set<String> filterRabbits;

    private Integer filterBeginDate; // ex 20150601
    private Integer filterEndDate; // ex 20150601

    // Group By
    private List<String> group;

    private String collection = "reports.statistic.day";

    public Set<String> getFilterRabbits() {
        return filterRabbits;
    }

    public void setFilterRabbits(Set<String> filterRabbits) {
        this.filterRabbits = filterRabbits;
    }

    public Integer getFilterEndDate() {
        return filterEndDate;
    }

    public void setFilterEndDate(Integer filterEndDate) {
        this.filterEndDate = filterEndDate;
    }

    public Integer getFilterBeginDate() {
        return filterBeginDate;
    }

    public void setFilterBeginDate(Integer filterBeginDate) {
        this.filterBeginDate = filterBeginDate;
    }

    public List<String> getGroup() {
        return group;
    }

    public void setGroup(List<String> group) {
        this.group = group;
    }

    @Override
    public List<String> getColumns(){
        List<String> columns = new ArrayList<>();
        for (String item : group){
            if (item.lastIndexOf(".") > -1){
                columns.add(item.substring(item.lastIndexOf(".") + 1));
            }
            else{
                columns.add(item);
            }
        }
        return columns;
    }

    @Override
    public String getTargetCollection() {
        return collection;
    }

    @Override
    public List<AggregationOperation> makeAggregationOp() {
        List<AggregationOperation> op = new ArrayList<>();

        // Filter : RabbitId
        if (getFilterRabbits() != null && !getFilterRabbits().isEmpty()){
            op.add(new MatchOperation(Criteria.where("rabbit").in(getFilterRabbits())));
        }

        // Filter : Date
        Criteria dateCriteria = null;
        if (getFilterBeginDate() != null){
            dateCriteria = Criteria.where("dateInt").gte(getFilterBeginDate());
        }
        if (getFilterEndDate() != null){
            if (dateCriteria == null){
                dateCriteria = Criteria.where("dateInt").lte(getFilterEndDate());
            }
            else{
                dateCriteria = dateCriteria.andOperator(Criteria.where("dateInt").lte(getFilterEndDate()));
            }
        }
        if (dateCriteria != null){
            op.add(new MatchOperation(dateCriteria));
        }

        // Group by
        if (group != null){
            op.add(new GroupOperation(Fields.fields((String[]) group.toArray())).count().as("count"));
            op.add(new SortOperation(new Sort(Sort.Direction.ASC, (String[]) group.toArray())));
        }
        return op;
    }

    @Override
    public String toString() {
        return "LogLevelAnalysisRequest{" +
                "filterRabbits=" + filterRabbits +
                ", filterBeginDate=" + filterBeginDate +
                ", filterEndDate=" + filterEndDate +
                ", group=" + group +
                ", collection='" + collection + '\'' +
                '}';
    }
}
