package com.tecacet.util.conversion;

import java.util.Map;

import org.apache.commons.beanutils.Converter;

public interface ConverterRegistry {

	void registerConverter(Class type, Converter converter);
	
	void deregister(Class type);
	
	Map<Class, Converter> getRegisteredConverters();
}
