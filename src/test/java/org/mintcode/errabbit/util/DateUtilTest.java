package org.mintcode.errabbit.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by soleaf on 9/29/15.
 */
public class DateUtilTest {

    /**
     * Get last day of month
     * @throws Exception
     */
    @Test
    public void testGetLastDayOfMonth() throws Exception {
        assertTrue(28 == DateUtil.getLastDayOfMonth(2014, 2));
        assertTrue(28 == DateUtil.getLastDayOfMonth(2015, 2));
        assertTrue(31 == DateUtil.getLastDayOfMonth(2015, 5));
        assertTrue(30 == DateUtil.getLastDayOfMonth(2015, 6));
        assertTrue(31 == DateUtil.getLastDayOfMonth(2015, 7));
        assertTrue(31 == DateUtil.getLastDayOfMonth(2015, 8));
        assertTrue(30 == DateUtil.getLastDayOfMonth(2015, 9));
        assertTrue(31 == DateUtil.getLastDayOfMonth(2015, 10));
    }
}