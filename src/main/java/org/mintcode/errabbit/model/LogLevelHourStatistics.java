package org.mintcode.errabbit.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * LogLevelHourStatistics
 * LogLevel Statistics hourly by rabbit
 * Log level aggregation
 * Created by soleaf on 2015. 2. 2..
 */

@Document(collection = "logs.statistic.hour")
@CompoundIndexes({@CompoundIndex(name = "by_id_date",def = "{rabbitId:1,year:1,month:1,day:1,hour:1}")})
public class LogLevelHourStatistics implements Serializable {

    /*
     {rabbitId : 'rabbitId'
                ,year : year
                ,month : month
                ,day : day
                ,hour : hour
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

    private Integer hour;

    private Integer level_DEBUG;

    private Integer level_INFO;

    private Integer level_TRACE;

    private Integer level_WARN;

    private Integer level_FATAL;

    private Integer level_ERROR;

    @Indexed
    private Integer dateInt;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getValZero(Integer i){
        return i == null ? 0 : i;
    }

    public Integer getLevel_DEBUG() {
        return getValZero(level_DEBUG);
    }

    public void setLevel_DEBUG(Integer level_DEBUG) {
        this.level_DEBUG = level_DEBUG;
    }

    public Integer getLevel_INFO() {
        return getValZero(level_INFO);
    }

    public void setLevel_INFO(Integer level_INFO) {
        this.level_INFO = level_INFO;
    }

    public Integer getLevel_TRACE() {
        return getValZero(level_TRACE);
    }

    public void setLevel_TRACE(Integer level_TRACE) {
        this.level_TRACE = level_TRACE;
    }

    public Integer getLevel_WARN() {
        return getValZero(level_WARN);
    }

    public void setLevel_WARN(Integer level_WARN) {
        this.level_WARN = level_WARN;
    }

    public Integer getLevel_FATAL() {
        return getValZero(level_FATAL);
    }

    public void setLevel_FATAL(Integer level_FATAL) {
        this.level_FATAL = level_FATAL;
    }

    public Integer getLevel_ERROR() {
        return getValZero(level_ERROR);
    }

    public void setLevel_ERROR(Integer level_ERROR) {
        this.level_ERROR = level_ERROR;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getDateInt() {
        return dateInt;
    }

    public void setDateInt(Integer dateInt) {
        this.dateInt = dateInt;
    }

    public void add(LogLevelHourStatistics statistics){

        if (level_DEBUG == null){
            level_DEBUG = 0;
        }
        if (level_TRACE == null){
            level_TRACE = 0;
        }
        if (level_INFO == null){
            level_INFO = 0;
        }
        if (level_WARN == null){
            level_WARN = 0;
        }
        if (level_FATAL == null){
            level_FATAL = 0;
        }
        if (level_ERROR == null){
            level_ERROR = 0;
        }
        level_DEBUG += statistics.getLevel_DEBUG();
        level_TRACE += statistics.getLevel_TRACE();
        level_INFO += statistics.getLevel_INFO();
        level_ERROR += statistics.getLevel_ERROR();
        level_WARN += statistics.getLevel_WARN();
        level_FATAL += statistics.getLevel_FATAL();
    }

    @Override
    public String toString() {
        return "LogLevelHourStatistics{" +
                "id=" + id +
                ", rabbitId='" + rabbitId + '\'' +
                ", year=" + year +
                ", day=" + day +
                ", month=" + month +
                ", hour=" + hour +
                ", level_DEBUG=" + level_DEBUG +
                ", level_INFO=" + level_INFO +
                ", level_TRACE=" + level_TRACE +
                ", level_WARN=" + level_WARN +
                ", level_FATAL=" + level_FATAL +
                ", level_ERROR=" + level_ERROR +
                '}';
    }
}
