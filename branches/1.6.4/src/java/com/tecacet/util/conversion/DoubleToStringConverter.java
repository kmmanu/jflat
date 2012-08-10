package com.tecacet.util.conversion;

import com.tecacet.util.conversion.ToStringConverter;

public class DoubleToStringConverter implements ToStringConverter<Double> {

    private static final String FLOAT_FORMAT = "%019.5f";
    private transient final String format;
    
    public DoubleToStringConverter() {
        this.format = FLOAT_FORMAT;
    }

    @Override
    public String convertToString(Double amount) {
        if (amount == null) {
            return null;
        }
        return String.format(format, amount);
    }

}
