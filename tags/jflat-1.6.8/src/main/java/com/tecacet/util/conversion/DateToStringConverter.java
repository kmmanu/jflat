package com.tecacet.util.conversion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToStringConverter implements ToStringConverter<Date> {

    private static final String DEFAULT_DATE_PATTERN = "dd-MM-yyyy";

    private DateFormat dateFormat;

    public DateToStringConverter(String dateFormatString) {
        this.dateFormat = new SimpleDateFormat(dateFormatString);
    }

    public DateToStringConverter() {
        this(DEFAULT_DATE_PATTERN);
    }

    @Override
    public String convert(Date value) {
        if (value == null) {
            return null;
        }
        return dateFormat.format(value);
    }

}
