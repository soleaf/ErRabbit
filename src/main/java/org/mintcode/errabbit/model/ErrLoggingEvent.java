package org.mintcode.errabbit.model;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by soleaf on 2/21/15.
 */
public class ErrLoggingEvent implements Serializable{

    public ErrLoggingEvent(){

    }

    public static ErrLoggingEvent fromLogingEvent(LoggingEvent loggingEvent){

        ErrLoggingEvent erLoggingEvent = new ErrLoggingEvent();

        erLoggingEvent.setCategoryName(loggingEvent.getLogger().getName());
        erLoggingEvent.setLevel(loggingEvent.getLevel().toString());
        erLoggingEvent.setMessage(loggingEvent.getMessage());
        erLoggingEvent.setRenderedMessage(loggingEvent.getRenderedMessage());
        erLoggingEvent.setThreadName(loggingEvent.getThreadName());
        erLoggingEvent.setLocationInfo(ErLocationInfo.fromLocationInfo(loggingEvent.getLocationInformation()));
        erLoggingEvent.setTimeStamp(loggingEvent.getTimeStamp());
        erLoggingEvent.setTimeStampDate(new Date(loggingEvent.getTimeStamp()));
        erLoggingEvent.setThrowableInfo(ErThrowableInformation.fromThrowableInformation(loggingEvent.getThrowableInformation()));

        return erLoggingEvent;

    }

    public static ErrLoggingEvent fromLog4jLogEvent(Log4jLogEvent log4jLogEvent){
        ErrLoggingEvent erLoggingEvent = new ErrLoggingEvent();

        erLoggingEvent.setCategoryName(log4jLogEvent.getLoggerName());
        erLoggingEvent.setLevel(log4jLogEvent.getLevel().toString());
        erLoggingEvent.setMessage(log4jLogEvent.getMessage());
        erLoggingEvent.setRenderedMessage(log4jLogEvent.getMessage().getFormattedMessage());
        erLoggingEvent.setThreadName(log4jLogEvent.getThreadName());
//        erLoggingEvent.setLocationInfo(ErLocationInfo.fromLocationInfo(log4jLogEvent.get));
        erLoggingEvent.setTimeStamp(log4jLogEvent.getTimeMillis());
        erLoggingEvent.setTimeStampDate(new Date(log4jLogEvent.getTimeMillis()));
        erLoggingEvent.setThrowableInfo(ErThrowableInformation.fromThrowableProxy(log4jLogEvent.getThrownProxy()));

        return erLoggingEvent;
    }

    public String categoryName;

    public String level;

    private Object message;

    private String renderedMessage;

    private String threadName;

    private ErLocationInfo locationInfo;

    private ErThrowableInformation throwableInfo;

    public long timeStamp;

    public Date timeStampDate;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getRenderedMessage() {
        return renderedMessage;
    }

    public void setRenderedMessage(String renderedMessage) {
        this.renderedMessage = renderedMessage;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public ErLocationInfo getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(ErLocationInfo locationInfo) {
        this.locationInfo = locationInfo;
    }

    public ErThrowableInformation getThrowableInfo() {
        return throwableInfo;
    }

    public void setThrowableInfo(ErThrowableInformation throwableInfo) {
        this.throwableInfo = throwableInfo;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Date getTimeStampDate() {
        return timeStampDate;
    }

    public void setTimeStampDate(Date timeStampDate) {
        this.timeStampDate = timeStampDate;
    }

    @Override
    public String toString() {
        return "ErrLoggingEvent{" +
                "categoryName='" + categoryName + '\'' +
                ", level='" + level + '\'' +
                ", message=" + message +
                ", renderedMessage='" + renderedMessage + '\'' +
                ", threadName='" + threadName + '\'' +
                ", locationInfo=" + locationInfo +
                ", throwableInfo=" + throwableInfo +
                ", timeStamp=" + timeStamp +
                ", timeStampDate=" + timeStampDate +
                '}';
    }
}
