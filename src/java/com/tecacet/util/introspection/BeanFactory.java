package com.tecacet.util.introspection;

/**
 *  Abstraction for creating beans
 * 
 * @author Dimitri Papaioannou
 *
 * @param <T>
 */
public interface BeanFactory {

    /**
     * Create a bean of the given type
     * 
     * @param type type of the bean
     * @return
     * @throws BeanCreationException if bean creation fails.
     */
    Object createBean(Class type) throws BeanCreationException;
}