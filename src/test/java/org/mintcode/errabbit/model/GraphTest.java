package org.mintcode.errabbit.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by soleaf on 9/29/15.
 */
public class GraphTest {

    @Test
    public void testGetColorSet1() throws Exception {
        LogLevelHourStatistics hs = new LogLevelHourStatistics();
        hs.setHour(1);
        hs.setLevel_DEBUG(1);
        Graph graph = new Graph();
        graph.add(hs);
        assertEquals(graph.getColorSet().size() , 1);
    }
    @Test
    public void testGetColorSet2() throws Exception {
        LogLevelHourStatistics hs = new LogLevelHourStatistics();
        hs.setHour(1);
        hs.setLevel_DEBUG(1);
        hs.setLevel_WARN(1);
        Graph graph = new Graph();
        graph.add(hs);
        assertEquals(graph.getColorSet().size() , 2);
    }

    @Test
    public void testGetGraph(){
        LogLevelHourStatistics hs = new LogLevelHourStatistics();
        hs.setHour(1);
        hs.setLevel_DEBUG(1);
        hs.setLevel_WARN(1);

        LogLevelHourStatistics hs2 = new LogLevelHourStatistics();
        hs2.setHour(2);
        hs2.setLevel_DEBUG(3);
        hs2.setLevel_WARN(2);

        Graph graph = new Graph();
        graph.add(hs);
        graph.add(hs2);

        assertEquals(graph.getColorSet().size() , 2);
        assertNotNull(graph.getGraph());
    }
}