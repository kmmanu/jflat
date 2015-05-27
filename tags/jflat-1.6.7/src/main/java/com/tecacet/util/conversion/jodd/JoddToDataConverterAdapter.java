package com.tecacet.util.conversion.jodd;

import jodd.typeconverter.TypeConverter;

import com.tecacet.util.conversion.DataConverter;

public class JoddToDataConverterAdapter<FROM, TO> implements
		DataConverter<FROM, TO> {

	private final TypeConverter<TO> typeConverter;

	public JoddToDataConverterAdapter(TypeConverter<TO> typeConverter) {
		super();
		this.typeConverter = typeConverter;
	}

	@Override
	public TO convert(FROM from) {
		return typeConverter.convert(from);
	}

}
