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

    public String toHTML(Boolean showRabbitID){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<li class='report' data-poload='/log/popover_data?id=%s'>", id.toString()));
            sb.append(String.format("<span class='time'>%s</span>", format.format(loggingEvent.timeStampDate)));
            sb.append(String.format("<span class='level %s %s'>%s</span>", loggingEvent.level,
                                                                (loggingEvent.getThrowableInfo() != null ? "has_exception" : ""),
                                                                loggingEvent.level));
            sb.append("<div class='contgroup'>");
                if (showRabbitID){
                    sb.append(String.format("<span class='rabbit_id'>%s</span>", rabbitId));
                }
                sb.append(String.format("<span class='categoryName'>%s</span>", loggingEvent.categoryName));
                sb.append(String.format("<span class='message'>%s</span>", loggingEvent.getRenderedMessage()));
            sb.append("</div>");
        sb.append("</li>");
        return sb.toString();
    }
}
