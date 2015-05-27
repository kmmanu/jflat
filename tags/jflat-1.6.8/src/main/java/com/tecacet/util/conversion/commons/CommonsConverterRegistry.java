package com.tecacet.util.conversion.commons;

import org.apache.commons.beanutils.ConvertUtils;

import com.tecacet.util.conversion.ConverterRegistry;
import com.tecacet.util.conversion.DataConverter;

public class CommonsConverterRegistry implements ConverterRegistry {

    private static final CommonsConverterRegistry INSTANCE = new CommonsConverterRegistry();

    private CommonsConverterRegistry() {
    }

    public static ConverterRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public void deregister(Class<?> type) {
        ConvertUtils.deregister(type);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <TO, FROM> void registerConverter(Class<FROM> type, DataConverter<TO, FROM> converter) {
        ConvertUtils.register(new DataConverterToCommonsAdapter(converter), type);

    }

}
