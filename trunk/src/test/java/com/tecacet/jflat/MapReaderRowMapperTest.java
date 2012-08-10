package com.tecacet.jflat;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class MapReaderRowMapperTest {

    @Test
    public void testGetRow() {
        ColumnMapping columnMapping = new ColumnPositionMapping(new String[] { "side", "dish","price" });
        MapReaderRowMapper mapper = new MapReaderRowMapper(columnMapping);
        String[] row = new String[] { "Bacon", "Eggs", "100" };
        Map<String, String> map = mapper.getRow(row, 1);
        assertEquals("Bacon", map.get("side"));
        assertEquals("Eggs", map.get("dish"));
        assertEquals("100", map.get("price"));
    }

}
