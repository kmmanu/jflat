package com.tecacet.jflat.util.conversion;

public class BooleanToStringConverter implements ToStringConverter<Boolean> {

    @Override
    public String convertToString(Boolean value) {
        if (value == null) {
            return null;
        }
        return value ? "Y" : "N";
    }

}
