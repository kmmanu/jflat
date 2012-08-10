package com.tecacet.util.introspection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.tecacet.util.introspection.commons.CommonsBeanUtilsPropertyAccessor;

public class CommonsBeanUtilsPropertyAccessorTest extends TestCase {

    CommonsBeanUtilsPropertyAccessor<Order> accessor = new CommonsBeanUtilsPropertyAccessor<Order>();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void testSetProperty() throws BeanIntrospectorException {
        DateConverter dateConverter = new DateConverter(null);
        dateConverter.setPattern("yyyy-MM-dd");
        ConvertUtils.register(dateConverter, Date.class);

        Order bean = new Order();

        accessor.setProperty(bean, "number", "1234");
        assertEquals("1234", bean.getNumber());
        assertEquals("1234", accessor.getProperty(bean, "number"));
        accessor.setProperty(bean, "quantity", "10");
        assertEquals(10, bean.getQuantity());
        assertEquals("10", accessor.getProperty(bean, "quantity"));
        accessor.setProperty(bean, "price", "15.99");
        assertEquals(15.99, bean.getPrice());
        assertEquals("15.99", accessor.getProperty(bean, "price"));
        accessor.setProperty(bean, "active", "true");
        assertTrue(bean.isActive());
        assertEquals("true", accessor.getProperty(bean, "active"));
        GregorianCalendar c = new GregorianCalendar(2007, Calendar.JULY, 4);
        accessor.setProperty(bean, "date", dateFormat.format(c.getTime()));
        assertEquals(c.getTime(), bean.getDate());
        assertEquals("Wed Jul 04 00:00:00 PDT 2007", accessor.getProperty(bean, "date"));

        accessor.setProperty(bean, "customer.name", "James");

        assertNotNull(accessor.getProperty(bean, "customer.name"));

        accessor.setProperty(bean, "customer.name", "James");
        assertEquals("James", bean.getCustomer().getName());
        assertEquals("James", accessor.getProperty(bean, "customer.name"));

    }

    public void testSetNullProperty() throws BeanIntrospectorException {
        Order bean = new Order();

        accessor.setProperty(bean, "number", null);
        assertNull(bean.getNumber());
        assertNull(accessor.getProperty(bean, "number"));
        accessor.setProperty(bean, "quantity", null);
        assertEquals(0, bean.getQuantity());
        assertEquals("0", accessor.getProperty(bean, "quantity"));
        accessor.setProperty(bean, "price", null);
        assertEquals(0.0, bean.getPrice());
        assertEquals("0.0", accessor.getProperty(bean, "price"));
        accessor.setProperty(bean, "active", null);
        assertFalse(bean.isActive());
        assertEquals("false", accessor.getProperty(bean, "active"));

        accessor.setProperty(bean, "date", null);
        assertNull(bean.getDate());

    }
}
