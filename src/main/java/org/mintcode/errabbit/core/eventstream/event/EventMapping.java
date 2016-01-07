package org.mintcode.errabbit.core.eventstream.event;

import org.mintcode.errabbit.core.eventstream.event.action.EventAction;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * EventMapping
 * Mapping condition with actions
 * Created by soleaf on 1/1/16.
 */
@Document(collection = "settings.event.mapping")
public class EventMapping {

    @DBRef
    private EventCondition condition;

    @DBRef
    private List<EventAction> actions = new ArrayList<>();

    public EventMapping(){

    }

    public EventMapping(EventCondition condition) {
        this.condition = condition;
    }

    public void addEventAction(EventAction action){
        actions.add(action);
    }

    public void removeEventAction(EventAction action){
        actions.remove(action);
    }

    public EventCondition getCondition() {
        return condition;
    }

    public void setCondition(EventCondition condition) {
        this.condition = condition;
    }

    public List<EventAction> getActions() {
        return actions;
    }

    public void setActions(List<EventAction> actions) {
        this.actions = actions;
    }
}