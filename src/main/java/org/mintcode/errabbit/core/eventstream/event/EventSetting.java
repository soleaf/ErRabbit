package org.mintcode.errabbit.core.eventstream.event;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by soleaf on 10/12/15.
 */
@Document(collection = "settings.notification")
public class EventSetting {

    @Id
    private ObjectId id;

    private String rabbitId;

    private Date regDate;

    private String name;

    private Boolean active;


    /**
     * Checking conditions
     */

    private Integer thresholdCount;

    private Integer rangeMinutes;

    private Integer sleepMinutesAfterNotice;

    private String matchLevel;

    private String matchClass;

    private String matchMessage;


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getRabbitId() {
        return rabbitId;
    }

    public void setRabbitId(String rabbitId) {
        this.rabbitId = rabbitId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getThresholdCount() {
        return thresholdCount;
    }

    public void setThresholdCount(Integer thresholdCount) {
        this.thresholdCount = thresholdCount;
    }

    public Integer getRangeMinutes() {
        return rangeMinutes;
    }

    public void setRangeMinutes(Integer rangeMinutes) {
        this.rangeMinutes = rangeMinutes;
    }

    public Integer getSleepMinutesAfterNotice() {
        return sleepMinutesAfterNotice;
    }

    public void setSleepMinutesAfterNotice(Integer sleepMinutesAfterNotice) {
        this.sleepMinutesAfterNotice = sleepMinutesAfterNotice;
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

    public String getMatchMessage() {
        return matchMessage;
    }

    public void setMatchMessage(String matchMessage) {
        this.matchMessage = matchMessage;
    }
}
