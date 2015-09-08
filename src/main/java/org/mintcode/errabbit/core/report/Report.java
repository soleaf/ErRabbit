package org.mintcode.errabbit.core.report;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.analysis.result.AnalysisResultSet;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by soleaf on 15. 8. 9..
 */
@Document(collection = "reports")
public class Report implements Serializable{

    @Id
    private ObjectId id;

    private Date sendTime;
    AnalysisResultSet logReport;
    AnalysisResultSet logLevelReport;

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

    public AnalysisResultSet getLogLevelReport() {
        return logLevelReport;
    }

    public void setLogLevelReport(AnalysisResultSet logLevelReport) {
        this.logLevelReport = logLevelReport;
    }
}
