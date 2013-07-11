package com.tecacet.util.conversion;

import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

public class CommonsConverterRegistry implements ConverterRegistry {

    private static final CommonsConverterRegistry INSTANCE = new CommonsConverterRegistry();
    
    private CommonsConverterRegistry() {
    }
    
    public static ConverterRegistry getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void registerConverter(Class type, Converter converter) {
        ConvertUtils.register(converter, type);
    }

    @Override
    public void deregister(Class type) {
        ConvertUtils.deregister(type);
    }

    @Override
    public Map<Class, Converter> getRegisteredConverters() {
        throw new UnsupportedOperationException();
    }

}
