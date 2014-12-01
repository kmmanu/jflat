package com.tecacet.util.conversion.commons;

import org.apache.commons.beanutils.Converter;

import com.tecacet.util.conversion.DataConverter;

public class DataConverterToCommonsAdapter<TO> implements Converter {

	private final DataConverter<Object, TO> converter;
	
	public DataConverterToCommonsAdapter(DataConverter<Object, TO> converter) {
		super();
		this.converter = converter;
	}

	@SuppressWarnings("rawtypes")
    @Override
	public Object convert(Class type, Object value) {
		return converter.convert(value);
	}

	

}
