package com.tecacet.util.conversion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.joda.time.LocalTime;

public class LocalTimeConverter extends AbstractConverter {

    private DateFormat format;

    public LocalTimeConverter(final String format) {
        super();
        this.format = new SimpleDateFormat(format);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object convertToType(final Class type, final Object o) {
        String value = (String) o;
        if (isEmpty(value)) {
            return null;
        }
        Date date;
        try {
            date = format.parse(value.trim());
        } catch (ParseException e) {
            throw new ConversionException(e);
        }
        return new LocalTime(date);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class getDefaultType() {
        return LocalTime.class;
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

}
