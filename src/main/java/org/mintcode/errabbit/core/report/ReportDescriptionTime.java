package org.mintcode.errabbit.core.report;

import java.io.Serializable;

/**
 * ReportDescriptionTime
 * Batch schedule
 * Created by soleaf on 15. 8. 9..
 */
public class ReportDescriptionTime implements Serializable {

    private Integer hour;

    /**
     * Batch time hour (24h)
     * @return
     */
    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public ReportDescriptionTime(Integer hour) {
        this.hour = hour;
    }
}
