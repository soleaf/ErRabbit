package org.mintcode.errabbit.core.rabbit.name;

import org.junit.Test;
import org.mintcode.errabbit.model.RabbitGroup;

import static org.junit.Assert.*;

/**
 * Created by soleaf on 9/29/15.
 */
public class RabbitGroupNameComparatorTest {

    @Test
    public void testCompare() throws Exception {
        RabbitGroup g1 = new RabbitGroup("A");
        RabbitGroup g2 = new RabbitGroup("B");

        RabbitGroupNameComparator rabbitGroupNameComparator = new RabbitGroupNameComparator();

        assertEquals(-1, rabbitGroupNameComparator.compare(g1, g2));
    }
}