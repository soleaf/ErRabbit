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
public interface EventAction{

    public String getId();
    public void setId(String id);

    /**
     * Run action
     * @param eventCondition
     * @param log
     * @return
     */
    public Boolean run(EventCondition eventCondition, Log log);

    /**
     * Copy action instance to run on eventstream
     * @return
     */
    public EventAction copy();

    /**
     * Get label made by user
     * @return
     */
    public String getName();
    public void setName(String name);

    /**
     * Get description for ui
     * @return
     */
    public String getDescription();

    /**
     * Identity type name (className)
     * @return
     */
    public String getTypeName();

    /**
     * Get occurred error message in run
     * @return null or error message string
     */
    public String getErrorMessage();

    /**
     * Defined setting UI elements
     * @return
     */
    public List<EventActionUIElement> getUIElements();

    /**
     * Check setting key/value is valid
     * @param dataSet
     * @return
     */
    public void validationUISetting(Map<String,String> dataSet) throws InvalidEventActionUISettingValueException;

    /**
     * Apply setting key/value
     * @param dataSet
     */
    public void settingFromUI(Map<String,String> dataSet) throws InvalidEventActionUISettingValueException;

}
