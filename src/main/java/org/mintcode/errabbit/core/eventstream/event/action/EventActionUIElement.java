package org.mintcode.errabbit.core.eventstream.event.action;

/**
 * Created by soleaf on 1/11/16.
 */
public class EventActionUIElement {

    public String name;
    public String label;
    public String defaultValue;
    public String valueType;
    public String help;
    public Boolean isRequired = false;
    public Object value;

    public EventActionUIElement(String name, String label, String valueType, String defaultValue, String help, Boolean isRequired, Object value) {
        this.name = name;
        this.label = label;
        this.defaultValue = defaultValue;
        this.valueType = valueType;
        this.help = help;
        this.isRequired = isRequired;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public Boolean getRequired() {
        return isRequired;
    }

    public void setRequired(Boolean required) {
        isRequired = required;
    }

    public EventActionUIElement(){

    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EventActionUIElement{" +
                "name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", valueType='" + valueType + '\'' +
                ", help='" + help + '\'' +
                ", isRequired=" + isRequired +
                ", value=" + value +
                '}';
    }
}
