package com.tecacet.util.conversion;

import java.util.Currency;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * Converts a currency code to a Currency object
 */
public class CurrencyConverter extends AbstractConverter {

    
    @SuppressWarnings("rawtypes")
    @Override
    protected Object convertToType(Class c, Object value) throws Throwable {
        if (value == null) {
            return null;
        }
        String code = (String) value;
        return Currency.getInstance(code);
    }

    @Override
    protected Class<?> getDefaultType() {
        return Currency.class;
    }

}
