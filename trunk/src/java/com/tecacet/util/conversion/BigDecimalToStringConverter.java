package com.tecacet.util.conversion;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.tecacet.util.conversion.ToStringConverter;

public class BigDecimalToStringConverter implements ToStringConverter<BigDecimal> {

    private static final String FLOAT_FORMAT = "%019.5f";
    private transient final String format;

    public BigDecimalToStringConverter() {
        this(FLOAT_FORMAT);
    }

    public BigDecimalToStringConverter(String format) {
        this.format = format;
    }

    @Override
    public String convertToString(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        //There is a Java bug when formatting certain big decimals:
        // http://bugs.sun.com/view_bug.do?bug_id=6476425
        BigDecimal safe = amount.setScale(5, RoundingMode.HALF_EVEN);
        return String.format(format, safe);
    }

}
