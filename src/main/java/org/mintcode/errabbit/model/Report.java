package org.mintcode.errabbit.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by soleaf on 2015. 2. 2..
 */
@Document(collection = "reports")
public class Report implements Serializable {

    @Id
    protected ObjectId id;

    // RabbitID
    @Indexed(unique = false)
    protected String rabbitId;

    // ErRabbitVersion
    protected String version;

    // LoggingEvent
    ErrLoggingEvent loggingEvent;

    // LoggingEventDateInt (20140101)
    @Indexed(unique = false)
    protected Integer loggingEventDateInt;

    // InBox Timed
    @Indexed(unique = false)
    protected Date collectedDate;

    public Report(){

    }

    public Report(String rabbitId, ErrLoggingEvent loggingEvent){
        this.rabbitId = rabbitId;
        this.loggingEvent = loggingEvent;
    }

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ErrLoggingEvent getLoggingEvent() {
        return loggingEvent;
    }

    public void setLoggingEvent(ErrLoggingEvent loggingEvent) {
        this.loggingEvent = loggingEvent;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        loggingEventDateInt = Integer.parseInt(format.format(loggingEvent.getTimeStampDate()));
    }

    public Date getCollectedDate() {
        return collectedDate;
    }

    public void setCollectedDate(Date collectedDate) {
        this.collectedDate = collectedDate;
    }

    public Integer getLoggingEventDateInt() {
        return loggingEventDateInt;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", rabbitId='" + rabbitId + '\'' +
                ", version='" + version + '\'' +
                ", loggingEvent=" + loggingEvent +
                ", loggingEventDateInt=" + loggingEventDateInt +
                ", collectedDate=" + collectedDate +
                '}';
    }
}
