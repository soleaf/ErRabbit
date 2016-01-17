package org.mintcode.errabbit.core.eventstream.event.action;

import org.mintcode.errabbit.core.eventstream.event.EventCondition;
import org.mintcode.errabbit.model.Log;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

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

    @Override
    public String getDescription() {
        return "NotificationEventAction";
    }

    public String getTypeName() {
        return getClass().getSimpleName();
    }

    @Override
    public List<EventActionUIElement> getUIElements() {
        return null;
    }

    @Override
    public void validationUISetting(Map<String, String> dataSet) {

    }

    @Override
    public void settingFromUI(Map<String, String> dataSet) {

    }
}
