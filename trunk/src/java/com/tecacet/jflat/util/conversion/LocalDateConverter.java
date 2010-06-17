package com.tecacet.jflat.util.conversion;

import java.util.Date;

import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.joda.time.LocalDate;

public class LocalDateConverter extends AbstractConverter {

    private transient final DateConverter dateConverter;

    public LocalDateConverter(String dateFormatString) {
        dateConverter = new DateConverter();
        dateConverter.setPattern(dateFormatString);
    }
    
    public LocalDateConverter(String[] dateFormatStrings) {
        dateConverter = new DateConverter();
        dateConverter.setPatterns(dateFormatStrings);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object convertToType(Class type, Object o) {
        String value = (String) o;
        if (isEmpty(value)) {
            return null;
        }
        Date date = (Date) dateConverter.convert(Date.class, o);
        return new LocalDate(date);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class getDefaultType() {
        return LocalDate.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object handleMissing(Class c) {
        return null;
    }
    
    private boolean isEmpty(String s) {
        if (s == null) {
            return true;
        }
        return s.trim().equals("");
    }

}
