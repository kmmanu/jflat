package com.tecacet.jflat;

import static org.junit.Assert.*;

import org.junit.Test;

public class FixedWidthLineMergerTest {

    @Test
    public void testMakeLine() {
        FixedWidthLineMerger merger = new FixedWidthLineMerger(new int[] { 2, 5 });
        String line = merger.makeLine(new String[] { "a", "b" });
        assertEquals(" a    b",line);
    }
}
