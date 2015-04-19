package org.mintcode.errabbit.core.report.dao;

import org.mintcode.errabbit.model.Report;

import java.util.List;

/**
 * Created by soleaf on 2015. 2. 19..
 */
public interface ReportRepositoryCustom {

    /**
     * Add Hour Statistic from report
     * @param report
     */
    public void insertHourStatistic(Report report);

}
