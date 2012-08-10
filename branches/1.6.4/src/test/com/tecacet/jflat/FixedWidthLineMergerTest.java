package com.tecacet.jflat;

import static org.junit.Assert.*;

import org.junit.Test;

public class FixedWidthLineMergerTest {

    @Test
    public void testMakeLine() throws LineMergerException{
        FixedWidthLineMerger merger = new FixedWidthLineMerger(new int[] { 2, 5 });
        String line = merger.makeLine(new String[] { "a", "b" });
        assertEquals("a b    ", line); //Left justified!
    }

    @Test(expected = TooManyFieldsException.class)
    public void testTooManyFields() throws LineMergerException {
        FixedWidthLineMerger merger = new FixedWidthLineMerger(new int[] { 2, 5 });
        merger.makeLine(new String[] { "12", "12345", "123456" });
    }
    
    @Test(expected = FieldTooWideException.class)
    public void testFieldTooWide() throws LineMergerException {
        FixedWidthLineMerger merger = new FixedWidthLineMerger(new int[] { 2, 5 });
        merger.makeLine(new String[] { "12", "123456"});
    }
    
    @Test
    public void testNullFields() throws LineMergerException {
        FixedWidthLineMerger merger = new FixedWidthLineMerger(new int[] { 1, 1, 1 });
        String line = merger.makeLine(new String[] { "a", null, "c" });
        assertEquals("a c", line);
    }

}
