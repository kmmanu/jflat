package com.tecacet.util.conversion;

import java.util.HashMap;
import java.util.Map;

public final class GenericConverterRegistry implements ConverterRegistry {

    private Map<Class<?>, DataConverter<?, ?>> converters = new HashMap<Class<?>, DataConverter<?, ?>>();

    private static final GenericConverterRegistry INSTANSE = new GenericConverterRegistry();

    public static ConverterRegistry getInstance() {
        return INSTANSE;
    }

    private GenericConverterRegistry() {

    }

    @Override
    public <FROM, TO> void registerConverter(Class<TO> type, DataConverter<FROM, TO> converter) {
        converters.put(type, converter);
    }

    @Override
    public void deregister(Class<?> type) {
        converters.remove(type);
    }

}
