package com.tecacet.jflat.util.conversion;

/**
 * Type converter that converts a type to String
 * 
 */
public interface ToStringConverter<T> {

    String convertToString(T value);
}
