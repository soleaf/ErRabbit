package org.mintcode.errabbit.core.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by soleaf on 6/28/15.
 */
public class LogAggregationRequest {

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

    @Override
    public String toString() {
        return "LogAggregationRequest{" +
                "filterRabbits='" + filterRabbits + '\'' +
                ", filterLevels=" + filterLevels +
                ", filterBeginDate=" + filterBeginDate +
                ", filterEndDate=" + filterEndDate +
                ", group=" + group +
                '}';
    }
}
