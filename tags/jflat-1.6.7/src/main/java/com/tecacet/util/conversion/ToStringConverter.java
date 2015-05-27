package com.tecacet.util.conversion;

/**
 * Type converter that converts a type to String.
 * 
 * @param T the type to be converted to String
 */
public interface ToStringConverter<T> extends DataConverter<T, String> {

	@Override
    String convert(T value);
}
