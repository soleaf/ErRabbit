package org.mintcode.errabbit.core.eventstream.event.action;

import org.mintcode.errabbit.core.eventstream.event.EventCondition;
import org.mintcode.errabbit.model.Log;

/**
 * Created by soleaf on 12/31/15.
 */
public interface EventAction {

    public Boolean run(EventCondition eventCondition, Log log);

}
