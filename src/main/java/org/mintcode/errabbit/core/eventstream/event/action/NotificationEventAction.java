package org.mintcode.errabbit.core.eventstream.event.action;

import org.mintcode.errabbit.core.eventstream.event.EventCondition;
import org.mintcode.errabbit.model.Log;

/**
 * Created by soleaf on 12/31/15.
 */
public class NotificationEventAction extends AbstractEventAction {

    @Override
    public Boolean run(EventCondition eventCondition, Log log) {
        return true;
    }
}
