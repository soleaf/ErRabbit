package org.mintcode.errabbit.core.eventstream.event.action;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

/**
 * Created by soleaf on 12/31/15.
 */
public abstract class AbstractEventAction implements EventAction {

    @Id
    String id;

    private String name;

    @Transient
    protected String errorMessage = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
