package com.tecacet.util.introspection;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapPropertyAccessorTest {

    @Test
    public void testSetGetProperty() throws BeanIntrospectorException {
        Map map = new HashMap();
        MapPropertyAccessor accessor = new MapPropertyAccessor();
        assertNull(accessor.getProperty(map, "any"));
        accessor.setProperty(map, "name", "Indigo");
        assertEquals("Indigo", accessor.getProperty(map, "name"));
    }

}
