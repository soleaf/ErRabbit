package org.mintcode.errabbit.core.eventstream.event;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by soleaf on 10/12/15.
 */
public class EventCondition {

    private Set<String> rabbitIdSet = new HashSet<>();

    /**
     * Checking conditions
     */

    private Integer thresholdCount = 0;

    private Integer periodSec = 0;

    private Integer sleepSecAfterAction = 0;

    private String matchLevel = null;

    private String matchClass = null;

    private String includeMessage = null;

    private Boolean hasException = false;

    private Boolean includeMessageRegex = false;

    public Set<String> getRabbitIdSet() {
        return rabbitIdSet;
    }

    public void setRabbitIdSet(Set<String> rabbitIdSet) {
        this.rabbitIdSet = rabbitIdSet;
    }

    public boolean containsRabbitId(String rabbitId){
        return this.rabbitIdSet.contains(rabbitId);
    }

    public void addRabbitId(String rabbitId){
        this.rabbitIdSet.add(rabbitId);
    }

    public void removeRabbitId(String rabbitId){
        this.rabbitIdSet.remove(rabbitId);
    }

    public Integer getThresholdCount() {
        return thresholdCount;
    }

    public void setThresholdCount(Integer thresholdCount) {
        this.thresholdCount = thresholdCount;
    }

    public Integer getPeriodSec() {
        return periodSec;
    }

    public void setPeriodSec(Integer periodSec) {
        this.periodSec = periodSec;
    }

    public Integer getSleepSecAfterAction() {
        return sleepSecAfterAction;
    }

    public void setSleepSecAfterAction(Integer sleepSecAfterAction) {
        this.sleepSecAfterAction = sleepSecAfterAction;
    }

    public String getMatchLevel() {
        return matchLevel;
    }

    public void setMatchLevel(String matchLevel) {
        this.matchLevel = matchLevel;
    }

    public String getMatchClass() {
        return matchClass;
    }

    public void setMatchClass(String matchClass) {
        this.matchClass = matchClass;
    }

    public String getIncludeMessage() {
        return includeMessage;
    }

    public void setIncludeMessage(String includeMessage) {
        this.includeMessage = includeMessage;
    }

    public Boolean getHasException() {
        return hasException;
    }

    public void setHasException(Boolean hasException) {
        this.hasException = hasException;
    }

    @Override
    public String toString() {
        return "EventCondition{" +
                "rabbitIdSet=" + rabbitIdSet +
                ", thresholdCount=" + thresholdCount +
                ", periodSec=" + periodSec +
                ", sleepSecAfterAction=" + sleepSecAfterAction +
                ", matchLevel='" + matchLevel + '\'' +
                ", matchClass='" + matchClass + '\'' +
                ", includeMessage='" + includeMessage + '\'' +
                ", hasException=" + hasException +
                ", includeMessageRegex=" + includeMessageRegex +
                '}';
    }
}
