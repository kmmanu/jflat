package com.tecacet.jflat.util;

import static org.junit.Assert.*;

import org.joda.time.LocalTime;
import org.junit.Test;

import com.tecacet.util.conversion.LocalTimeConverter;

public class LocalTimeConverterTest {

    @Test
    public void testConvertToTypeClassObject() {
       LocalTimeConverter converter = new LocalTimeConverter("hh:mm a");
       LocalTime t = (LocalTime) converter.convert(LocalTime.class, "9:21 PM");
       System.err.println(t);
    }

}
