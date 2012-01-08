package com.tecacet.util.conversion;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.Converter;

public class GenericConverterRegistry implements ConverterRegistry {

    private Map<Class, Converter> converters = new HashMap<Class, Converter>();

    private static final GenericConverterRegistry INSTANSE = new GenericConverterRegistry();

    public static ConverterRegistry getInstance() {
        return INSTANSE;
    }

    private GenericConverterRegistry() {

    }

    @Override
    public void registerConverter(Class type, Converter converter) {
        converters.put(type, converter);
    }

    @Override
    public void deregister(Class type) {
        converters.remove(type);
    }

    @Override
    public Map<Class, Converter> getRegisteredConverters() {
        return converters;
    }

}
