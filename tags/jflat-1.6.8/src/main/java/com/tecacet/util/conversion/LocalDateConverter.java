package com.tecacet.util.conversion;

import org.joda.time.LocalDate;

import com.tecacet.util.conversion.commons.CommonsLocalDateConverter;

public class LocalDateConverter implements DataConverter<String, LocalDate>{

    private final CommonsLocalDateConverter commonsConverter;
    
    public LocalDateConverter(String dateFormatString) {
        super();
        this.commonsConverter = new CommonsLocalDateConverter(dateFormatString);
    }

    public LocalDateConverter(String[] dateFormatStrings) {
        super();
        this.commonsConverter = new CommonsLocalDateConverter(dateFormatStrings);
    }
    
    @Override
    public LocalDate convert(String from) {
        return (LocalDate) commonsConverter.convert(LocalDate.class, from);
    }

    
    
}
