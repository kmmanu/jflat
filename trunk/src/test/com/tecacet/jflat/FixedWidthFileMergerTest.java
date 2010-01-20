package com.tecacet.jflat;

import static org.junit.Assert.*;

import org.junit.Test;

public class FixedWidthFileMergerTest {

    @Test
    public void testMakeLine() {
        FixedWidthFileMerger merger = new FixedWidthFileMerger(new int[] { 2, 5 });
        String line = merger.makeLine(new String[] { "a", "b" });
        assertEquals(" a    b",line);
    }
}
