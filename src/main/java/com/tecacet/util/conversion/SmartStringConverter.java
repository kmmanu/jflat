package com.tecacet.util.conversion;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * commons beanutils does not have real support for conversion of arbitrary
 * types to strings. This is a replacement for the default beanutils String
 * converters that allows arbitrary conversion of data types to strings by
 * registering ToStringConverters.
 * 
 */
public class SmartStringConverter extends AbstractConverter {

    private Map<Class, ToStringConverter> registry = new HashMap<Class, ToStringConverter>();

    /**
     * Register a ToString converter for a class. The converter will apply to
     * all subtypes and implementations
     * 
     * @param type
     *            the supertype for which this converter is applicable
     * @param converter
     */
    public void registerConverter(Class type, ToStringConverter converter) {
        registry.put(type, converter);
    }

    public void deregisterConverter(Class type) {
        registry.remove(type);
    }

    @Override
    protected Object convertToType(Class clazz, Object value) throws Throwable {
        return convertToString(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String convertToString(Object value) {
        if (value == null) {
            return null;
        }
        Class type = value.getClass();

        for (Class clazz : registry.keySet()) {
            if (clazz.isAssignableFrom(type)) {
                ToStringConverter converter = registry.get(clazz);
                return converter.convertToString(value);
            }
        }
        return value.toString();
    }
    
    @Override
    protected Class getDefaultType() {
        return String.class;
    }

}
