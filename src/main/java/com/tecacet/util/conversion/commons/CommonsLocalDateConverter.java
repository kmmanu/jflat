package com.tecacet.util.conversion.commons;

import java.util.Date;

import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.joda.time.LocalDate;

public class CommonsLocalDateConverter extends AbstractConverter {

    private transient final DateConverter dateConverter;

    public CommonsLocalDateConverter(String dateFormatString) {
        dateConverter = new DateConverter();
        dateConverter.setPattern(dateFormatString);
    }

    public CommonsLocalDateConverter(String[] dateFormatStrings) {
        dateConverter = new DateConverter();
        dateConverter.setPatterns(dateFormatStrings);
    }

    @Override
    protected Object convertToType(Class type, Object o) {
        String value = (String) o;
        if (isEmpty(value)) {
            return null;
        }
        Date date = (Date) dateConverter.convert(Date.class, o);
        return new LocalDate(date);
    }

    @Override
    protected Class getDefaultType() {
        return LocalDate.class;
    }

    @Override
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
