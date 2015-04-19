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

}
