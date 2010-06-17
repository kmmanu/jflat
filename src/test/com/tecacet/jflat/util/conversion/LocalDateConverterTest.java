package com.tecacet.jflat.util.conversion;

import static org.junit.Assert.*;

import org.apache.commons.beanutils.ConversionException;
import org.joda.time.LocalDate;
import org.junit.Test;

public class LocalDateConverterTest {

    private LocalDateConverter converter = new LocalDateConverter("MM/dd/yyyy");

    @Test
    public void testConvertToTypeClassObject() {
        LocalDate result = (LocalDate) converter.convert(LocalDate.class, "02/13/1999");
        LocalDate localDate = new LocalDate(1999, 2, 13);
        assertEquals(localDate, result);
    }

    @Test
    public void testConvertWithMutlipleFormats() {
        LocalDate referenceDate = new LocalDate(1999, 2, 13);
    	String[] dateFormats = {"MM/dd/yyyy", "MM-dd-yyyy"};
    	LocalDateConverter multiFormatConverter = new LocalDateConverter(dateFormats);
    	
        LocalDate dateWithSlashes = (LocalDate) multiFormatConverter.convert(LocalDate.class, "02/13/1999");
        assertEquals("Converted date from MM/dd/yyyy format", referenceDate, dateWithSlashes);
        
        LocalDate dateWithHyphens = (LocalDate) multiFormatConverter.convert(LocalDate.class, "02-13-1999");
        assertEquals("Converted date from MM-dd-yyyy format", referenceDate, dateWithHyphens);
    }
    
    @Test
    public void testConvertWrongFormat() {
        try {
            converter.convert(LocalDate.class, "02-13-1999");
            fail();
        } catch (ConversionException ce) {
            assertEquals("Error converting 'String' to 'Date' using pattern 'MM/dd/yyyy'", ce.getMessage());
        }

    }
    
    @Test
    public void testConvertNull() {
        LocalDate result = (LocalDate) converter.convert(LocalDate.class, null);
        assertEquals(null, result);
    }

    @Test
    public void testGetDefaultType() {
        assertEquals(LocalDate.class, converter.getDefaultType());
    }

}
