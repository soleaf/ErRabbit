package org.mintcode.errabbit.core.eventstream.event;

import org.mintcode.errabbit.core.eventstream.event.action.EventAction;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * EventMapping
 * Mapping condition with actions
 * Created by soleaf on 1/1/16.
 */
@Document(collection = "event.mapping")
public class EventMapping {


    @Id
    private String id;

    private EventCondition condition;

    @DBRef
    private List<EventAction> actions = new ArrayList<>();

    private Boolean active = false;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventMapping(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "EventMapping{" +
                "condition=" + condition +
                ", actions=" + actions +
                ", active=" + active +
                ", name='" + name + '\'' +
                '}';
    }
}