package com.tecacet.util.conversion;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class SmartStringConverterTest {

	@Test
	public void testConverter() {
		SmartStringConverter converter = new SmartStringConverter();
		converter.registerConverter(Date.class, new DateToStringConverter());
		converter.registerConverter(Double.class, new DoubleToStringConverter());
		Calendar c = new GregorianCalendar(2010, 2, 3);
		assertEquals("03-03-2010", converter.convert(c.getTime()));
	}

}
