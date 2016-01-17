package org.mintcode.errabbit.core.eventstream.event.action;

import org.junit.Ignore;
import org.junit.Test;
import org.mintcode.errabbit.model.ErrLoggingEvent;
import org.mintcode.errabbit.model.Log;

import java.util.Date;

/**
 * Created by soleaf on 1/8/16.
 */
public class SlackNotificationEventActionTest{

    @Ignore
    @Test
    public void testRun() throws Exception {
        SlackNotificationEventAction action = new SlackNotificationEventAction();
        action.setChannel("#test");
        action.setHost("http://loalhost:8080");
        action.setWebhookURL("https://hooks.slack.com/services/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");

        Log log = new Log();
        log.setRabbitId("junit");
        log.setCollectedDate(new Date());
        ErrLoggingEvent errLoggingEvent = new ErrLoggingEvent();
        errLoggingEvent.setLevel("DEBUG");
        errLoggingEvent.setTimeStampDate(new Date());
        errLoggingEvent.setCategoryName("org.mintcode.errabit");
        errLoggingEvent.setRenderedMessage("This is error message");
        log.setLoggingEvent(errLoggingEvent);

        action.run(null, log);
    }
}