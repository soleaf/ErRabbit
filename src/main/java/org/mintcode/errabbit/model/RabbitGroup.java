package org.mintcode.errabbit.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * RabbitGroup
 * Created by soleaf on 15. 9. 4..
 */
@Document(collection = "rabbits.group")
public class RabbitGroup implements Serializable {

    @Id
    private ObjectId id;

    // Name
    private String name;

    // Used on UI
    @Transient
    private List<Rabbit> rabbitSet;

    public RabbitGroup(){

    }

    // Get NoneGroup
    public static RabbitGroup noneGroup(){
        return new NoneRabbitGroup();
    }

    // RabbitGroup with name
    public RabbitGroup(String name) {
        this.name = name;
    }

    /**
     * Get name
     * @return
     */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Id
     * @return
     */
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Get rabbits on group
     * @return
     */
    public List<Rabbit> getRabbits() {
        return rabbitSet;
    }

    /**
     * Set rabbits on group
     * @param rabbitSet
     */
    public void setRabbits(List<Rabbit> rabbitSet) {
        this.rabbitSet = rabbitSet;
    }

    @Override
    public String toString() {
        return "RabbitGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RabbitGroup that = (RabbitGroup) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
