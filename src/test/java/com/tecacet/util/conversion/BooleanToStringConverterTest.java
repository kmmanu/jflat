package com.tecacet.util.conversion;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BooleanToStringConverterTest {

    @Test
    public void testConvertToString() {
        BooleanToStringConverter converter = new BooleanToStringConverter();
        assertEquals("N", converter.convert(false));
        assertEquals("Y", converter.convert(true));
        
        converter.setNoString("No");
        converter.setYesString("Si");
        assertEquals("No", converter.convert(false));
        assertEquals("Si", converter.convert(true));
    }

}
