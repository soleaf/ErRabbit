package org.mintcode.errabbit.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Log
 * has LoggingInformation
 * Created by soleaf on 2015. 2. 2..
 */
@Document(collection = "logs")
@CompoundIndexes({
        @CompoundIndex(name = "date_id", def = "{'loggingEventDateInt': 1, 'rabbitId': 1, '_id':-1}"),
        @CompoundIndex(name = "date_id_level", def = "{'loggingEventDateInt': 1, 'rabbitId': 1, 'loggingEvent.level':1, '_id':-1}"),
        @CompoundIndex(name = "date_id_cate", def = "{'loggingEventDateInt': 1, 'rabbitId': 1, 'loggingEvent.categoryName':1, '_id':-1}"),
        @CompoundIndex(name = "date_id_cate_level", def = "{'loggingEventDateInt': 1, 'rabbitId': 1, 'loggingEvent.level':1, 'loggingEvent.categoryName':1, '_id':-1}")
})
public class Log implements Serializable {

    @Id
    protected ObjectId id;

    // RabbitID
    @Indexed(unique = false)
    protected String rabbitId;

    // LoggingEvent
    ErrLoggingEvent loggingEvent;

    // LoggingEventDateInt (20140101)
    @Indexed(unique = false)
    protected Integer loggingEventDateInt;

    // InBox Timed
    @Indexed(unique = false)
    protected Date collectedDate;

    protected LoggerSpecificData specificData;

    protected LoggerType type;

    public Log(){

    }

    /**
     * Make log from rabbitId, erLoggingEvent
     * @param rabbitId
     * @param loggingEvent
     */
    public Log(String rabbitId, ErrLoggingEvent loggingEvent){
        this.rabbitId = rabbitId;
        this.loggingEvent = loggingEvent;
    }

    /**
     * Id
     * @return
     */
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * RabbitId
     * @return
     */
    public String getRabbitId() {
        return rabbitId;
    }
    public void setRabbitId(String rabbitId) {
        this.rabbitId = rabbitId;
    }

    /**
     * Logger specific data
     * @return
     */
    public LoggerSpecificData getSpecificData() {
        return specificData;
    }
    public void setSpecificData(LoggerSpecificData specificData) {
        this.specificData = specificData;
    }

    /**
     * Log's Logger type
     * Default value is Log4j. Because bellow 1.1.0 only support Log4j, This value is null
     * @return
     */
    public LoggerType getType() {
        return null == type ? LoggerType.Log4j : type;
    }

    public void setType(LoggerType type) {
        this.type = type;
    }

    /**
     * LoggingEvent
     * @return
     */
    public ErrLoggingEvent getLoggingEvent() {
        return loggingEvent;
    }

    public void setLoggingEvent(ErrLoggingEvent loggingEvent) {
        this.loggingEvent = loggingEvent;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        loggingEventDateInt = Integer.parseInt(format.format(loggingEvent.getTimeStampDate()));
    }

    /**
     * Collected Date
     * @return
     */
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
        return "Log{" +
                "id=" + id +
                ", rabbitId='" + rabbitId + '\'' +
                ", loggingEvent=" + loggingEvent +
                ", loggingEventDateInt=" + loggingEventDateInt +
                ", collectedDate=" + collectedDate +
                '}';
    }

    /**
     * To Html
     * Used on console (web socket)
     * todo : remove from here!, And make new decorator class
     * @param showRabbitID
     * @return
     */
    public String toHTML(Boolean showRabbitID){

        Calendar cal = Calendar.getInstance();
        cal.setTime(getCollectedDate());
        Integer y = cal.get(Calendar.YEAR);
        Integer m = cal.get(Calendar.MONTH) + 1;
        Integer d = cal.get(Calendar.DAY_OF_MONTH);

        String filterlevelURL = String.format("/log/list.err?id=%s&y=%d&m=%d&d=%d&filter=true&filter_level=%s",
                rabbitId, y, m, d, loggingEvent.level);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<li class='log' data-e='%s' data-poload='/log/popover_data?id=%s'>",
                loggingEvent.getThrowableInfo() != null ? "true" : "" ,id.toString()));
            sb.append(String.format("<span class='time' data-toggle='popover' data-placement='right' data-trigger='hover' title='Application time' data-content='Server collecting time : %s'>%s</span>", format.format(collectedDate), format.format(loggingEvent.timeStampDate)));
            sb.append(String.format("<span class='level %s %s' data-url='%s'>%s</span>", loggingEvent.level,
                                                                loggingEvent.getThrowableInfo() != null ? "has_exception" : "",
                                                                filterlevelURL,
                                                                loggingEvent.level));
            sb.append("<div class='contgroup'>");
                if (showRabbitID){
                    sb.append(String.format("<span class='rabbit_id' data-toggle='tooltip' data-placement='right' title='%s'>%s</span>", rabbitId, rabbitId));
                }

                String filterClassURL = String.format("/log/list.err?id=%s&y=%d&m=%d&d=%d&filter=true&filter_level=%s&filter_class=%s",
                        rabbitId, y, m, d, loggingEvent.level, loggingEvent.getCategoryName());

                sb.append(String.format("<span class='categoryName' data-url='%s'>%s</span>", filterClassURL ,loggingEvent.categoryName));
                sb.append(String.format("<span class='message'>%s</span>", loggingEvent.getRenderedMessage()));
            sb.append("</div>");
        sb.append("</li>");
        return sb.toString();
    }
}
