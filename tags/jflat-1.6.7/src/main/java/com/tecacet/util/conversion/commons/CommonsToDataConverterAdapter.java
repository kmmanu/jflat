package com.tecacet.util.conversion.commons;

import org.apache.commons.beanutils.Converter;

import com.tecacet.util.conversion.DataConverter;

public class CommonsToDataConverterAdapter<FROM, TO> implements
		DataConverter<FROM, TO> {

	private final Class<TO> type;
	private final Converter converter;

	public CommonsToDataConverterAdapter(Class<TO> type, Converter converter) {
		super();
		this.type = type;
		this.converter = converter;
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public TO convert(FROM from) {
		return (TO) converter.convert(type, from);
	}

}
