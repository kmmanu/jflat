package com.tecacet.util.introspection.commons;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.NestedNullException;

import com.tecacet.util.introspection.BeanIntrospectorException;
import com.tecacet.util.introspection.PropertyAccessor;

/**
 * Implementation of BeanIntrospector that uses commons-beanutils
 * 
 */
public class CommonsBeanUtilsPropertyAccessor<T> implements PropertyAccessor<T> {

    protected BeanUtilsBean beanUtilsBean;

    static {
        BeanUtilsBean.setInstance(EnhancedBeanUtilsBean.getInstance());
    }

    /**
     * Get property using BeanUtils.getProperty. Note that properties are always
     * returned in String form Returns null if nested property is null.
     * 
     */
    @Override
    public Object getProperty(T bean, String propertyName) throws BeanIntrospectorException {
        try {
            return BeanUtils.getProperty(bean, propertyName);
        } catch (NestedNullException e) {
            return null;
        } catch (IllegalAccessException e) {
            throw new BeanIntrospectorException(e);
        } catch (InvocationTargetException e) {
            throw new BeanIntrospectorException(e);
        } catch (NoSuchMethodException e) {
            throw new BeanIntrospectorException(e);
        }
    }

    /**
     * 
     * @throws IllegalArgumentException
     *             if attempting to set a property to a null object
     */
    @Override
    public void setProperty(T bean, String propertyName, Object value) throws BeanIntrospectorException {
        try {
            BeanUtils.setProperty(bean, propertyName, value);
        } catch (IllegalAccessException e) {
            throw new BeanIntrospectorException(e);
        } catch (InvocationTargetException e) {
            throw new BeanIntrospectorException(e);
        }
    }

}
