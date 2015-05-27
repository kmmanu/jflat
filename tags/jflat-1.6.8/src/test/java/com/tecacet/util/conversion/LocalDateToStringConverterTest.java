package com.tecacet.util.conversion;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.Test;

public class LocalDateToStringConverterTest {

    @Test
    public void testConvertToString() {
        LocalDateToStringConverter converter = new LocalDateToStringConverter("MM/dd/yyyy");
        LocalDate localDate = new LocalDate(1999, 2, 13);
        String result = converter.convert(localDate);
        assertEquals("02/13/1999", result);
    }

}
