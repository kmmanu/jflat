package com.tecacet.util.introspection.commons;

import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.tecacet.jflat.util.conversion.ConverterRegistry;

public class CommonsConverterRegistry implements ConverterRegistry {

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
		throw new NotImplementedException();
	}

}
