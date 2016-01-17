package org.mintcode.errabbit.core.eventstream.event.action;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.util.StringUtils;

import java.util.Map;

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

    public void validationUISetting(Map<String, String> dataSet) throws InvalidEventActionUISettingValueException {
        if (!StringUtils.hasLength(dataSet.get("name"))){
            throw new InvalidEventActionUISettingValueException("invalid value for name");
        }
    }

    public void settingFromUI(Map<String, String> dataSet) throws InvalidEventActionUISettingValueException {
        name = dataSet.get("name");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AbstractEventAction{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEventAction that = (AbstractEventAction) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
