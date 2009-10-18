package com.tecacet.util.introspection;

/**
 *  Abstraction for creating beans
 * 
 * @author Dimitri Papaioannou
 *
 * @param <T>
 */
public interface BeanFactory {

    Object createBean(Class type) throws BeanCreationException;
}