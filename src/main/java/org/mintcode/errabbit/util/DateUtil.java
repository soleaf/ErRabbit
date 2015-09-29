package org.mintcode.errabbit.util;

import java.util.Calendar;
import java.util.Date;

/**
 * DateUtil
 * Created by soleaf on 4/6/15.
 */
public class DateUtil {

    /**
     * Get last day integer of month
     * @param year
     * @param month
     * @return
     */
    public static Integer getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

}
