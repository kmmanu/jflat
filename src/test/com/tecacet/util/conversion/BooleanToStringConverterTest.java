package com.tecacet.util.conversion;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tecacet.util.conversion.BooleanToStringConverter;

public class BooleanToStringConverterTest {

    @Test
    public void testConvertToString() {
        BooleanToStringConverter converter = new BooleanToStringConverter();
        assertEquals("N", converter.convertToString(false));
        assertEquals("Y", converter.convertToString(true));
        
        converter.setNoString("No");
        converter.setYesString("Si");
        assertEquals("No", converter.convertToString(false));
        assertEquals("Si", converter.convertToString(true));
    }

}
