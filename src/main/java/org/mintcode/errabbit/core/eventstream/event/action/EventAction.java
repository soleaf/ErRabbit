package org.mintcode.errabbit.core.eventstream.event.action;

import org.mintcode.errabbit.core.eventstream.event.EventCondition;
import org.mintcode.errabbit.model.Log;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by soleaf on 12/31/15.
 */
@Document(collection = "event.action")
public interface EventAction{

    public Boolean run(EventCondition eventCondition, Log log);

    public EventAction copy();

    public String getName();
    public void setName(String name);

    public String getTypeName();

    public String getErrorMessage();

}
