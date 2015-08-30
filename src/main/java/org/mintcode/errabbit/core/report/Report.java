package org.mintcode.errabbit.core.report;

import org.mintcode.errabbit.core.analysis.result.AnalysisResult;
import org.mintcode.errabbit.core.analysis.result.AnalysisResultSet;

import java.util.Date;

/**
 * Created by soleaf on 15. 8. 9..
 */
public class Report {

    private Date sendTime;
    AnalysisResultSet logReport;
    AnalysisResultSet logLevelReport;

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
