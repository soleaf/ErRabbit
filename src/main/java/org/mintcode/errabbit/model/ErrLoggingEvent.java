package org.mintcode.errabbit.model;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;

import java.io.Serializable;
import java.util.Date;

/**
 * ErrLoggingEvent
 * It is equivalent to org.apache.log4j.spi.LoggingEvent
 * (convert to save repository)
 * Created by soleaf on 2/21/15.
 */
public class ErrLoggingEvent implements Serializable{

    private static final long serialVersionUID = 1L;

    public String categoryName;

    public String level;

    private String renderedMessage;

    private String threadName;

    private ErLocationInfo locationInfo;

    private ErThrowableInformation throwableInfo;

    public long timeStamp;

    public Date timeStampDate;

    public ErrLoggingEvent(){

    }

    /**
     * Make from org.apache.log4j.spi.LoggingEvent (Log4j 1.x)
     * @param loggingEvent
     * @return
     */
    public static ErrLoggingEvent fromLoggingEvent(LoggingEvent loggingEvent){

        ErrLoggingEvent erLoggingEvent = new ErrLoggingEvent();

        erLoggingEvent.setCategoryName(loggingEvent.getLoggerName());
        erLoggingEvent.setLevel(loggingEvent.getLevel().toString());
        erLoggingEvent.setRenderedMessage(loggingEvent.getRenderedMessage());
        erLoggingEvent.setThreadName(loggingEvent.getThreadName());
        erLoggingEvent.setLocationInfo(ErLocationInfo.fromLocationInfo(loggingEvent.getLocationInformation()));
        erLoggingEvent.setTimeStamp(loggingEvent.getTimeStamp());
        erLoggingEvent.setTimeStampDate(new Date(loggingEvent.getTimeStamp()));

        // Optional throwable information
        if (loggingEvent.getThrowableInformation() != null){
            erLoggingEvent.setThrowableInfo(ErThrowableInformation.fromThrowableInformation(loggingEvent.getThrowableInformation()));
        }
        return erLoggingEvent;

    }

    /**
     * Make from org.apache.logging.log4j.core.impl.Log4jLogEvent (Log4j 2.x)
     * @param log4jLogEvent
     * @return
     */
    public static ErrLoggingEvent fromLog4jLogEvent(Log4jLogEvent log4jLogEvent){
        ErrLoggingEvent erLoggingEvent = new ErrLoggingEvent();

        erLoggingEvent.setCategoryName(log4jLogEvent.getLoggerName());
        erLoggingEvent.setLevel(log4jLogEvent.getLevel().toString());
        erLoggingEvent.setRenderedMessage(log4jLogEvent.getMessage().getFormattedMessage());
        erLoggingEvent.setThreadName(log4jLogEvent.getThreadName());
        erLoggingEvent.setTimeStamp(log4jLogEvent.getTimeMillis());
        erLoggingEvent.setTimeStampDate(new Date(log4jLogEvent.getTimeMillis()));
        if (log4jLogEvent.getThrownProxy() != null){
            erLoggingEvent.setThrowableInfo(ErThrowableInformation.fromThrowableProxy(log4jLogEvent.getThrownProxy()));
        }

        return erLoggingEvent;
    }

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
                ", renderedMessage='" + renderedMessage + '\'' +
                ", threadName='" + threadName + '\'' +
                ", locationInfo=" + locationInfo +
                ", throwableInfo=" + throwableInfo +
                ", timeStamp=" + timeStamp +
                ", timeStampDate=" + timeStampDate +
                '}';
    }
}
