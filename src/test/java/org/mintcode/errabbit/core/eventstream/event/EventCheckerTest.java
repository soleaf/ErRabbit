package org.mintcode.errabbit.core.eventstream.event;

import org.junit.Test;
import org.mintcode.errabbit.core.eventstream.event.action.NotificationEventAction;
import org.mintcode.errabbit.model.ErrLoggingEvent;
import org.mintcode.errabbit.model.Log;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Created by soleaf on 1/2/16.
 */
public class EventCheckerTest {

    @Test
    public void testLevelCheck() throws Exception {

        EventChecker ec = new EventChecker();

        assertFalse(ec.levelCheck("WARN", "TRACE"));
        assertFalse(ec.levelCheck("WARN", "TRACE"));
        assertFalse(ec.levelCheck("WARN", "DEBUG"));
        assertFalse(ec.levelCheck("WARN", "INFO"));
        assertTrue(ec.levelCheck("WARN", "WARN"));
        assertTrue(ec.levelCheck("WARN", "FATAL"));
        assertTrue(ec.levelCheck("WARN", "ERROR"));

    }

    @Test
    public void testCheckMatched1(){

        Log log = new Log();
        log.setRabbitId("junit");
        log.setCollectedDate(new Date());

        EventCondition eventCondition = new EventCondition();
        eventCondition.addRabbitId("junit");
        eventCondition.setSleepSecAfterAction(1);
        eventCondition.setPeriodSec(1);

        EventMapping eventMapping = new EventMapping(eventCondition);
        eventMapping.addEventAction(new NotificationEventAction());

        EventChecker ec = new EventChecker(eventMapping);
        assertTrue(ec.check(log));

    }

    @Test
    public void testCheckNotMatched1(){

        // Matched :
        // Not Matched : rabbit_id

        Log log = new Log();
        log.setRabbitId("junit1");
        log.setCollectedDate(new Date());

        EventCondition eventCondition = new EventCondition();
        eventCondition.addRabbitId("junit");
        eventCondition.setSleepSecAfterAction(1);
        eventCondition.setPeriodSec(1);

        EventMapping eventMapping = new EventMapping(eventCondition);
        eventMapping.addEventAction(new NotificationEventAction());

        EventChecker ec = new EventChecker(eventMapping);
        assertFalse(ec.check(log));

    }

    @Test
    public void testCheckMatched2(){

        // Matched : rabbit_id, level
        // Not Matched :

        Log log = new Log();
        log.setRabbitId("junit");
        log.setCollectedDate(new Date());
        ErrLoggingEvent errLoggingEvent = new ErrLoggingEvent();
        errLoggingEvent.setLevel("ERROR");
        errLoggingEvent.setTimeStampDate(new Date());
        log.setLoggingEvent(errLoggingEvent);

        EventCondition eventCondition = new EventCondition();
        eventCondition.addRabbitId("junit");
        eventCondition.setMatchLevel("WARN");
        eventCondition.setSleepSecAfterAction(1);
        eventCondition.setPeriodSec(1);

        EventMapping eventMapping = new EventMapping(eventCondition);
        eventMapping.addEventAction(new NotificationEventAction());

        EventChecker ec = new EventChecker(eventMapping);
        assertTrue(ec.check(log));

    }

    @Test
    public void testCheckNotMatched2(){

        // Matched : rabbit_id
        // Not Matched : level

        Log log = new Log();
        log.setRabbitId("junit");
        log.setCollectedDate(new Date());
        ErrLoggingEvent errLoggingEvent = new ErrLoggingEvent();
        errLoggingEvent.setLevel("INFO");
        errLoggingEvent.setTimeStampDate(new Date());
        log.setLoggingEvent(errLoggingEvent);

        EventCondition eventCondition = new EventCondition();
        eventCondition.addRabbitId("junit");
        eventCondition.setMatchLevel("FATAL");
        eventCondition.setSleepSecAfterAction(1);
        eventCondition.setPeriodSec(1);

        EventMapping eventMapping = new EventMapping(eventCondition);
        eventMapping.addEventAction(new NotificationEventAction());

        EventChecker ec = new EventChecker(eventMapping);
        assertFalse(ec.check(log));

    }

    @Test
    public void testCheckMatched3(){

        // Matched : rabbit_id, level, class
        // Not Matched :

        Log log = new Log();
        log.setRabbitId("junit");
        log.setCollectedDate(new Date());
        ErrLoggingEvent errLoggingEvent = new ErrLoggingEvent();
        errLoggingEvent.setLevel("ERROR");
        errLoggingEvent.setTimeStampDate(new Date());
        errLoggingEvent.setCategoryName("org.mintcode.errabit");
        log.setLoggingEvent(errLoggingEvent);

        EventCondition eventCondition = new EventCondition();
        eventCondition.addRabbitId("junit");
        eventCondition.setMatchLevel("WARN");
        eventCondition.setMatchClass("org.mintcode.errabit");
        eventCondition.setSleepSecAfterAction(1);
        eventCondition.setPeriodSec(1);

        EventMapping eventMapping = new EventMapping(eventCondition);
        eventMapping.addEventAction(new NotificationEventAction());

        EventChecker ec = new EventChecker(eventMapping);
        assertTrue(ec.check(log));

    }

    @Test
    public void testCheckNotMatched3(){

        // Matched : rabbit_id, level
        // Not Matched : class

        Log log = new Log();
        log.setRabbitId("junit");
        log.setCollectedDate(new Date());
        ErrLoggingEvent errLoggingEvent = new ErrLoggingEvent();
        errLoggingEvent.setLevel("WARN");
        errLoggingEvent.setTimeStampDate(new Date());
        errLoggingEvent.setCategoryName("org.mintcode.errabit2");
        log.setLoggingEvent(errLoggingEvent);

        EventCondition eventCondition = new EventCondition();
        eventCondition.addRabbitId("junit");
        eventCondition.setMatchLevel("ERROR");
        eventCondition.setMatchClass("org.mintcode.errabit");
        eventCondition.setSleepSecAfterAction(1);
        eventCondition.setPeriodSec(1);

        EventMapping eventMapping = new EventMapping(eventCondition);
        eventMapping.addEventAction(new NotificationEventAction());

        EventChecker ec = new EventChecker(eventMapping);
        assertFalse(ec.check(log));

    }

    @Test
    public void testCheckMatched4(){

        // Matched : rabbit_id, level, class, message
        // Not Matched :

        Log log = new Log();
        log.setRabbitId("junit");
        log.setCollectedDate(new Date());
        ErrLoggingEvent errLoggingEvent = new ErrLoggingEvent();
        errLoggingEvent.setLevel("ERROR");
        errLoggingEvent.setTimeStampDate(new Date());
        errLoggingEvent.setCategoryName("org.mintcode.errabit");
        errLoggingEvent.setRenderedMessage("This is error message");
        log.setLoggingEvent(errLoggingEvent);

        EventCondition eventCondition = new EventCondition();
        eventCondition.addRabbitId("junit");
        eventCondition.setMatchLevel("WARN");
        eventCondition.setMatchClass("org.mintcode.errabit");
        eventCondition.setIncludeMessage("error");
        eventCondition.setSleepSecAfterAction(1);
        eventCondition.setPeriodSec(1);

        EventMapping eventMapping = new EventMapping(eventCondition);
        eventMapping.addEventAction(new NotificationEventAction());

        EventChecker ec = new EventChecker(eventMapping);
        assertTrue(ec.check(log));

    }

    @Test
    public void testCheckNotMatched4(){

        // Matched : rabbit_id, level, class
        // Not Matched : message

        Log log = new Log();
        log.setRabbitId("junit");
        log.setCollectedDate(new Date());
        ErrLoggingEvent errLoggingEvent = new ErrLoggingEvent();
        errLoggingEvent.setLevel("ERROR");
        errLoggingEvent.setTimeStampDate(new Date());
        errLoggingEvent.setCategoryName("org.mintcode.errabit");
        errLoggingEvent.setRenderedMessage("This is error message");
        log.setLoggingEvent(errLoggingEvent);

        EventCondition eventCondition = new EventCondition();
        eventCondition.addRabbitId("junit");
        eventCondition.setMatchLevel("WARN");
        eventCondition.setMatchClass("org.mintcode.errabit");
        eventCondition.setIncludeMessage("warning");
        eventCondition.setSleepSecAfterAction(1);
        eventCondition.setPeriodSec(1);

        EventMapping eventMapping = new EventMapping(eventCondition);
        eventMapping.addEventAction(new NotificationEventAction());

        EventChecker ec = new EventChecker(eventMapping);
        assertFalse(ec.check(log));

    }

    @Test
    public void testCheckTreshold(){

        // Matched : rabbit_id, level, class
        // Not Matched : message

        Log log = new Log();
        log.setRabbitId("junit");
        log.setCollectedDate(new Date());
        ErrLoggingEvent errLoggingEvent = new ErrLoggingEvent();
        errLoggingEvent.setLevel("ERROR");
        errLoggingEvent.setTimeStampDate(new Date());
        errLoggingEvent.setCategoryName("org.mintcode.errabit");
        errLoggingEvent.setRenderedMessage("This is error message");
        log.setLoggingEvent(errLoggingEvent);

        EventCondition eventCondition = new EventCondition();
        eventCondition.addRabbitId("junit");
        eventCondition.setMatchLevel("WARN");
        eventCondition.setMatchClass("org.mintcode.errabit");
        eventCondition.setIncludeMessage("error");
        eventCondition.setSleepSecAfterAction(1);
        eventCondition.setThresholdCount(5);
        eventCondition.setPeriodSec(5);

        EventMapping eventMapping = new EventMapping(eventCondition);
        eventMapping.addEventAction(new NotificationEventAction());

        EventChecker ec = new EventChecker(eventMapping);
        assertFalse(ec.check(log));
        assertFalse(ec.check(log));
        assertFalse(ec.check(log));
        assertFalse(ec.check(log));
        assertTrue(ec.check(log));

    }

    @Test
    public void testCheckTresholdNotReached(){

        // Matched : rabbit_id, level, class
        // Not Matched : message

        Log log = new Log();
        log.setRabbitId("junit");
        log.setCollectedDate(new Date());
        ErrLoggingEvent errLoggingEvent = new ErrLoggingEvent();
        errLoggingEvent.setLevel("ERROR");
        errLoggingEvent.setTimeStampDate(new Date());
        errLoggingEvent.setCategoryName("org.mintcode.errabit");
        errLoggingEvent.setRenderedMessage("This is error message");
        log.setLoggingEvent(errLoggingEvent);

        EventCondition eventCondition = new EventCondition();
        eventCondition.addRabbitId("junit");
        eventCondition.setMatchLevel("WARN");
        eventCondition.setMatchClass("org.mintcode.errabit");
        eventCondition.setIncludeMessage("error");
        eventCondition.setSleepSecAfterAction(1);
        eventCondition.setThresholdCount(6);
        eventCondition.setPeriodSec(5);

        EventMapping eventMapping = new EventMapping(eventCondition);
        eventMapping.addEventAction(new NotificationEventAction());

        EventChecker ec = new EventChecker(eventMapping);
        assertFalse(ec.check(log));
        assertFalse(ec.check(log));
        assertFalse(ec.check(log));
        assertFalse(ec.check(log));
        assertFalse(ec.check(log));

    }

    @Test
    public void testCheckSleep() throws InterruptedException {

        // Matched : rabbit_id, level, class
        // Not Matched : message

        Log log = new Log();
        log.setRabbitId("junit");
        log.setCollectedDate(new Date());
        ErrLoggingEvent errLoggingEvent = new ErrLoggingEvent();
        errLoggingEvent.setLevel("ERROR");
        errLoggingEvent.setTimeStampDate(new Date());
        errLoggingEvent.setCategoryName("org.mintcode.errabit");
        errLoggingEvent.setRenderedMessage("This is error message");
        log.setLoggingEvent(errLoggingEvent);

        EventCondition eventCondition = new EventCondition();
        eventCondition.addRabbitId("junit");
        eventCondition.setMatchLevel("WARN");
        eventCondition.setMatchClass("org.mintcode.errabit");
        eventCondition.setIncludeMessage("error");
        eventCondition.setSleepSecAfterAction(1);
        eventCondition.setThresholdCount(1);
        eventCondition.setPeriodSec(5);

        EventMapping eventMapping = new EventMapping(eventCondition);
        eventMapping.addEventAction(new NotificationEventAction());

        EventChecker ec = new EventChecker(eventMapping);
        assertTrue(ec.check(log));
        assertFalse(ec.check(log));
        assertTrue(ec.isSleep());

        Thread.sleep(2000);
        assertFalse(ec.isSleep());
        assertTrue(ec.check(log));
    }
}