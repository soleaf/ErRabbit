package org.mintcode.errabbit.util;

import java.util.Calendar;
import java.util.Date;

/**
 * DateUtil
 * Created by soleaf on 4/6/15.
 */
public class DateUtil {

    /**
     * Get last date of month
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDateOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month-1); // warn : jan.=1
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * Get last day integer of month
     * @param year
     * @param month
     * @return
     */
    public static Integer getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month-1); // warn : jan.=1
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

}
