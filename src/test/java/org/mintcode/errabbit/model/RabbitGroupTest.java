package org.mintcode.errabbit.model;

import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by soleaf on 9/29/15.
 */
public class RabbitGroupTest {

    // None group validation
    @Test
    public void testNoneGroup() throws Exception {
        RabbitGroup group = RabbitGroup.noneGroup();
        assertEquals(group, new NoneRabbitGroup());
        assertEquals("(no group)", group.getName());
    }

    @Test
    public void testGetRabbits() throws Exception {
        Rabbit r1 = new Rabbit();
        r1.setId("1");
        Rabbit r2 = new Rabbit();
        r2.setId("2");
        List<Rabbit> rabbitList = new ArrayList<>();
        rabbitList.add(r1);
        rabbitList.add(r2);

        RabbitGroup group = new RabbitGroup();
        group.setRabbits(rabbitList);
        assertEquals(rabbitList, group.getRabbits());
    }

    // Same objectId -> same group
    @Test
    public void testEquals() throws Exception {

        ObjectId id = new ObjectId();

        RabbitGroup group1 = new RabbitGroup();
        group1.setId(id);
        RabbitGroup group2 = new RabbitGroup();
        group2.setId(id);
        assertEquals(group1, group2);
    }
}