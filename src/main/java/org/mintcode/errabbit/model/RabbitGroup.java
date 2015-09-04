package org.mintcode.errabbit.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by soleaf on 15. 9. 4..
 */
@Document(collection = "rabbits.group")
public class RabbitGroup implements Serializable {

    @Id
    private ObjectId id;

    private String name;

    public RabbitGroup(){

    }

    public RabbitGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RabbitGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
