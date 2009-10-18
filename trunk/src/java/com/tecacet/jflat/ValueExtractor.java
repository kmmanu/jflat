package com.tecacet.jflat;

/**
 * 
 * @author Dimitri Papaioannou
 *
 */
public interface ValueExtractor<T> {

    String getValue(T bean);
}
