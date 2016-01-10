package org.mintcode.errabbit.core.eventstream.event.action;

import org.mintcode.errabbit.core.eventstream.event.EventCondition;
import org.mintcode.errabbit.model.Log;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by soleaf on 12/31/15.
 */
@Document(collection = "event.action")
public class NotificationEventAction extends AbstractEventAction {

    @Override
    public Boolean run(EventCondition eventCondition, Log log) {
        return true;
    }

    @Override
    public EventAction copy() {
        return null;
    }
    public String getTypeName() {
        return getClass().getSimpleName();
    }
}
