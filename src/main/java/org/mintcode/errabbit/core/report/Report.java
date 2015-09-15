package org.mintcode.errabbit.core.report;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.analysis.result.AnalysisResultSet;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Created by soleaf on 15. 8. 9..
 */
@Document(collection = "reports")
public class Report implements Serializable{

    private static final DateFormat format = new SimpleDateFormat("yyyy.MM.dd");

    @Id
    private ObjectId id;
    private Date sendTime;
    private Date targetDate;
    private Boolean read = false;
    private AnalysisResultSet logReport;
    private Set<String> targets;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public AnalysisResultSet getLogReport() {
        return logReport;
    }

    public void setLogReport(AnalysisResultSet logReport) {
        this.logReport = logReport;
    }

    public Boolean getRead() {
        return read == null ? false : read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Set<String> getTargets() {
        return targets;
    }

    public void setTargets(Set<String> targets) {
        this.targets = targets;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getTargetDateWithFormat(){
        return format.format(targetDate);
    }

    private Calendar targetDateCal(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);
        return cal;
    }

    public Integer getTargetYear(){
        return targetDateCal().get(Calendar.YEAR);
    }
    public Integer getTargetMonth(){
        return targetDateCal().get(Calendar.MONTH);
    }
    public Integer getTargetDay(){
        return targetDateCal().get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", sendTime=" + sendTime +
                ", targetDate=" + targetDate +
                ", read=" + read +
                ", logReport=" + logReport +
                ", targets=" + targets +
                '}';
    }
}
