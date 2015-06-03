package org.mintcode.errabbit.model;

import org.springframework.data.annotation.Id;
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

    /**
     * Getter and Setter
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Boolean getCollectionOnlyException() {
        return collectionOnlyException;
    }

    public void setCollectionOnlyException(Boolean collectionOnlyException) {
        this.collectionOnlyException = collectionOnlyException;
    }
}
