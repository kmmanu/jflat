package com.tecacet.util.conversion;

import java.util.HashMap;
import java.util.Map;

/**
 * commons beanutils does not have real support for conversion of arbitrary
 * types to strings. This is a replacement for the default beanutils String
 * converters that allows arbitrary conversion of data types to strings by
 * registering ToStringConverters.
 * 
 */
public class SmartStringConverter implements ToStringConverter<Object> {

	private Map<Class<?>, ToStringConverter<?>> registry = new HashMap<Class<?>, ToStringConverter<?>>();

	/**
	 * Register a ToString converter for a class. The converter will apply to
	 * all subtypes and implementations
	 * 
	 * @param type
	 *            the supertype for which this converter is applicable
	 * @param converter
	 */
	public void registerConverter(Class<?> type, ToStringConverter<?> converter) {
		registry.put(type, converter);
	}

	public void deregisterConverter(Class<?> type) {
		registry.remove(type);
	}

	public boolean supports(Class<?> type) {
		return registry.containsKey(type);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public String convert(Object from) {
		if (from == null) {
			return null;
		}
		Class<?> type = from.getClass();

		for (Class<?> clazz : registry.keySet()) {
			if (clazz.isAssignableFrom(type)) {
				ToStringConverter converter = registry.get(clazz);
				return converter.convert(from);
			}
		}
		return from.toString();
	}

}
