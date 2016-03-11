package org.mintcode.errabbit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Rabbit
 * Rabbis is a user's artifact of using ErRabbit Appender.
 * Created by soleaf on 2014. 11. 8..
 */
@Document(collection = "rabbits")
public class Rabbit implements Serializable {

    @Id
    private String id;

    // Identity
    private String label;

    // Registered Date
    private Date regDate = new Date();

    // Read all reports
    private Boolean read = false;

    // BasePackage
    private String basePackage;

    // Collect only log has Throwable exception;
    private Boolean collectionOnlyException;

    // Hide on console
    private Boolean hideOnConsole;

    // Logger type
    private LoggerType loggerType;

    // Group
    @DBRef
    private RabbitGroup group;

    /**
     * Getter and Setter
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Label
     * Now, label is not settable on UI, use id instead label.(support at next time)
     * @return
     */
    public String getLabel() {
        if (label == null){
            return id;
        }
        else{
            return label;
        }
    }
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Register date
     * @return
     */
    public Date getRegDate() {
        return regDate;
    }
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    /**
     * Read all logs
     * @return
     */
    public Boolean getRead() {
        return read;
    }
    public void setRead(Boolean read) {
        this.read = read;
    }

    /**
     * Base package
     * ex) org.mintcode.errabbit
     * @return
     */
    public String getBasePackage() {
        return basePackage;
    }
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * Collect only exception on queue listener
     * @return
     */
    public Boolean getCollectionOnlyException() {
        return collectionOnlyException;
    }
    public void setCollectionOnlyException(Boolean collectionOnlyException) {
        this.collectionOnlyException = collectionOnlyException;
    }

    public LoggerType getLoggerType() {
        return loggerType == null ? LoggerType.Log4j : loggerType;
    }

    public void setLoggerType(LoggerType loggerType) {
        this.loggerType = loggerType;
    }

    /**
     * This rabbit's group
     * @return
     */
    public RabbitGroup getGroup() {
        if (group == null){
            return RabbitGroup.noneGroup();
        }
        else{
            return group;
        }
    }
    public void setGroup(RabbitGroup group) {
        this.group = group;
    }

    /**
     * Hide logs related this rabbit on console
     * @return
     */
    public Boolean getHideOnConsole() {
        return hideOnConsole == null ? false : hideOnConsole;
    }
    public void setHideOnConsole(Boolean hideOnConsole) {
        this.hideOnConsole = hideOnConsole;
    }

    @Override
    public String toString() {
        return "Rabbit{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", regDate=" + regDate +
                ", read=" + read +
                ", basePackage='" + basePackage + '\'' +
                ", collectionOnlyException=" + collectionOnlyException +
                ", hideOnConsole=" + hideOnConsole +
                ", loggerType=" + loggerType +
                ", group=" + group +
                '}';
    }
}
