package com.tecacet.util.conversion;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateTimeToStringConverter implements ToStringConverter<DateTime> {

    private static final String DEFAULT_DATE_PATTERN = "HH:mm:ss";

    private String dateFormat;

    public DateTimeToStringConverter(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateTimeToStringConverter() {
        this(DEFAULT_DATE_PATTERN);
    }

    @Override
    public String convert(DateTime value) {
        if (value == null) {
            return null;
        }
        return value.toString(DateTimeFormat.forPattern(dateFormat));
    }

}
