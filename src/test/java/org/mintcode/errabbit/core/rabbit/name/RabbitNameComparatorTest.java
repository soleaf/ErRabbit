package org.mintcode.errabbit.core.rabbit.name;

import org.junit.Test;
import org.mintcode.errabbit.model.Rabbit;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by soleaf on 9/29/15.
 */
public class RabbitNameComparatorTest {

    @Test
    public void testCompare() throws Exception {

        Rabbit r1 = new Rabbit();
        r1.setId("A");

        Rabbit r2 = new Rabbit();
        r2.setId("B");

        RabbitNameComparator comparator = new RabbitNameComparator();
        assertEquals(-1, comparator.compare(r1, r2));

    }
}