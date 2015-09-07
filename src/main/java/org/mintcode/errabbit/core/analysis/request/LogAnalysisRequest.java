package org.mintcode.errabbit.core.analysis.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by soleaf on 6/28/15.
 */
public class LogAnalysisRequest implements AnalysisRequest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Filter (null = All)
    private Set<String> filterRabbits;
    private Set<String> filterLevels = new HashSet<>();
    private Integer filterBeginDate; // ex 20150601
    private Integer filterEndDate; // ex 20150601

    // Group By
    public List<String> group;

    public Set<String> getFilterRabbits() {
        return filterRabbits;
    }

    public void setFilterRabbits(Set<String> filterRabbits) {
        this.filterRabbits = filterRabbits;
    }

    public Set<String> getFilterLevels() {
        return filterLevels;
    }

    public void setFilterLevels(Set<String> filterLevels) {
        this.filterLevels = filterLevels;
    }

    public Integer getFilterBeginDate() {
        return filterBeginDate;
    }

    public void setFilterBeginDate(Integer filterBeginDate) {
        this.filterBeginDate = filterBeginDate;
    }

    public Integer getFilterEndDate() {
        return filterEndDate;
    }

    public void setFilterEndDate(Integer filterEndDate) {
        this.filterEndDate = filterEndDate;
    }

    public List<String> getGroup() {
        return group;
    }

    public void setGroup(List<String> group) {
        this.group = group;
    }

    /**
     * Columns for View
     * Clean group name to make leave only last element name
     * ex) loggingEvent.level > level
     * @return
     */
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

    public String getTargetCollection(){
        return "logs";
    }

    /**
     * Make MongoAggregationOperations from LogAnalysisRequest
     * @return
     */
    public  List<AggregationOperation> makeAggregationOp(){

        List<AggregationOperation> op = new ArrayList<>();

        // Filter : RabbitId
        if (getFilterRabbits() != null && !getFilterRabbits().isEmpty()){
            logger.trace("rabbit " + getFilterRabbits());
            op.add(new MatchOperation(Criteria.where("rabbit").in(getFilterRabbits())));
        }

        // Filter : Levels
        if (getFilterLevels().size() > 0){
            logger.trace("levels " + getFilterLevels());
            op.add(new MatchOperation(Criteria.where("loggingEvent.level").in(getFilterLevels())));
        }

        // Filter : Date
        Criteria dateCriteria = null;
        if (getFilterBeginDate() != null){
            logger.trace("loggingEventDateInt gte " + getFilterBeginDate());
            dateCriteria = Criteria.where("loggingEventDateInt").gte(getFilterBeginDate());
        }
        if (getFilterEndDate() != null){
            if (dateCriteria == null){
                logger.trace("loggingEventDateInt lte " + getFilterEndDate());
                dateCriteria = Criteria.where("loggingEventDateInt").lte(getFilterEndDate());
            }
            else{
                logger.trace("loggingEventDateInt lte " + getFilterEndDate());
                dateCriteria = dateCriteria.andOperator(Criteria.where("loggingEventDateInt").lte(getFilterEndDate()));
            }
        }
        if (dateCriteria != null){
            op.add(new MatchOperation(dateCriteria));
        }

        // Group by
        if (group != null){
            logger.debug("group > " + group);
            String[] fieldsStr = new String[group.size()];
            for (int i = 0 ; i < group.size() ; i++){
                fieldsStr[i] = group.get(i);
            }

            Fields files = Fields.fields(fieldsStr);
//            Fields files = Fields.fields((String[]) group.toArray());
            op.add(new GroupOperation(files).count().as("count"));
            op.add(new SortOperation(new Sort(Sort.Direction.ASC, fieldsStr)));
//            op.add(new SortOperation(new Sort(Sort.Direction.ASC, (String[]) group.toArray())));
        }

        return op;
    }


    @Override
    public String toString() {
        return "LogAnalysisRequest{" +
                "filterRabbits='" + filterRabbits + '\'' +
                ", filterLevels=" + filterLevels +
                ", filterBeginDate=" + filterBeginDate +
                ", filterEndDate=" + filterEndDate +
                ", group=" + group +
                '}';
    }
}
