package org.mintcode.errabbit.core.collect.parser.impl;

import org.junit.Test;
import org.mintcode.errabbit.core.collect.parser.impl.PythonLogDeserializer;

import static org.junit.Assert.*;

/**
 * Created by soleaf on 1/31/16.
 */
public class PythonLogDeserializerTest {

    @Test
    public void testDeserialize() throws Exception {

    }

    @Test
    public void testGetFilename() throws Exception {

        PythonLogDeserializer ds = new PythonLogDeserializer();
        assertEquals("testModule.py", ds.getFilename("/Users/soleaf/Dropbox/ErRabbitLoggingHandler/testModule.py"));
        assertEquals("testModule.py", ds.getFilename("C:\\Users\\soleaf\\Dropbox\\ErRabbitLoggingHandler\\testModule.py"));
    }
}