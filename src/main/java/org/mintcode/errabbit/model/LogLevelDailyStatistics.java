package org.mintcode.errabbit.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * LogLevelDailyStatistics
 * Logging Statistics Daily by rabbit
 * Log level aggregation
 * Created by soleaf on 2015. 2. 2..
 */

@Document(collection = "logs.statistic.day")
@CompoundIndexes({@CompoundIndex(name = "by_id_date",def = "{'rabbitId':1,'year':1,'month':1,'day':1}")})
public class LogLevelDailyStatistics implements Serializable {

    /*
     {rabbitId : 'rabbitId'
                ,year : year
                ,month : month
                ,day : day
                ,level_ERROR : n
                ,level_INFO : n
                ...}
         */

    @Id
    private ObjectId id;

    @Indexed
    private String rabbitId;

    private Integer year;

    private Integer day;

    private Integer month;

    @Indexed
    private Integer dateInt;

    private Integer level_DEBUG = 0;

    private Integer level_INFO = 0;

    private Integer level_TRACE = 0;

    private Integer level_WARN = 0;

    private Integer level_FATAL = 0;

    private Integer level_ERROR = 0;

    /**
     * Get Id
     * @return
     */
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Get rabbitId
     * @return
     */
    public String getRabbitId() {
        return rabbitId;
    }
    public void setRabbitId(String rabbitId) {
        this.rabbitId = rabbitId;
    }

    /**
     * Get year
     * @return
     */
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Get day
     * @return
     */
    public Integer getDay() {
        return day;
    }
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * Get month
     * @return
     */
    public Integer getMonth() {
        return month;
    }
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * Get level debug's counts
     * @return
     */
    public Integer getLevel_DEBUG() {
        return level_DEBUG;
    }
    public void setLevel_DEBUG(Integer level_DEBUG) {
        this.level_DEBUG = level_DEBUG;
    }

    /**
     * Get level info's counts
     * @return
     */
    public Integer getLevel_INFO() {
        return level_INFO;
    }
    public void setLevel_INFO(Integer level_INFO) {
        this.level_INFO = level_INFO;
    }

    /**
     * Get level trace's counts
     * @return
     */
    public Integer getLevel_TRACE() {
        return level_TRACE;
    }
    public void setLevel_TRACE(Integer level_TRACE) {
        this.level_TRACE = level_TRACE;
    }

    /**
     * Get level warn's counts
     * @return
     */
    public Integer getLevel_WARN() {
        return level_WARN;
    }
    public void setLevel_WARN(Integer level_WARN) {
        this.level_WARN = level_WARN;
    }

    /**
     * Get level fatal's counts
     * @return
     */
    public Integer getLevel_FATAL() {
        return level_FATAL;
    }
    public void setLevel_FATAL(Integer level_FATAL) {
        this.level_FATAL = level_FATAL;
    }

    /**
     * Get level error's counts
     * @return
     */
    public Integer getLevel_ERROR() {
        return level_ERROR;
    }
    public void setLevel_ERROR(Integer level_ERROR) {
        this.level_ERROR = level_ERROR;
    }

    /**
     * Get date as int
     * example 20150205
     * @return
     */
    public Integer getDateInt() {
        return dateInt;
    }
    public void setDateInt(Integer dateInt) {
        this.dateInt = dateInt;
    }

    /**
     * Calculating Sum All logs
     * @return
     */
    public int calcTotal(){
        return level_DEBUG
                + level_INFO
                + level_TRACE
                + level_WARN
                + level_FATAL
                + level_ERROR;
    }

    @Override
    public String toString() {
        return "LogLevelDailyStatistics{" +
                "id=" + id +
                ", rabbitId='" + rabbitId + '\'' +
                ", year=" + year +
                ", day=" + day +
                ", month=" + month +
                ", dateInt=" + dateInt +
                ", level_DEBUG=" + level_DEBUG +
                ", level_INFO=" + level_INFO +
                ", level_TRACE=" + level_TRACE +
                ", level_WARN=" + level_WARN +
                ", level_FATAL=" + level_FATAL +
                ", level_ERROR=" + level_ERROR +
                '}';
    }
}
