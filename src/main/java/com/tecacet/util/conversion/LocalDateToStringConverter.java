package com.tecacet.util.conversion;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class LocalDateToStringConverter implements ToStringConverter<LocalDate> {

    private static final String DEFAULT_DATE_PATTERN = "dd-MM-yyyy";

    private String dateFormat;
    
    public LocalDateToStringConverter(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    public LocalDateToStringConverter() {
        this(DEFAULT_DATE_PATTERN);
    }
    
    @Override
    public String convert(LocalDate value) {
        if (value == null) {
            return null;
        }
        return value.toString(DateTimeFormat.forPattern(dateFormat));
    }

}
