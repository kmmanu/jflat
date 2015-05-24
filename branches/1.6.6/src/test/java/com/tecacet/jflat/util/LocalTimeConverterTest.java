package com.tecacet.jflat.util;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalTime;
import org.junit.Test;

import com.tecacet.util.conversion.LocalTimeConverter;

public class LocalTimeConverterTest {

    @Test
    public void testConvertToTypeClassObject() {
       LocalTimeConverter converter = new LocalTimeConverter("hh:mm a");
       LocalTime t =  converter.convert( "9:21 PM");
       assertEquals("21:21:00.000",t.toString());
    }

}
