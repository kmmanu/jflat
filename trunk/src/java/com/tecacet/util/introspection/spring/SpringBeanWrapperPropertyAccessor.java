package com.tecacet.util.introspection.spring;

import org.springframework.beans.BeanWrapperImpl;

import com.tecacet.util.introspection.BeanIntrospectorException;
import com.tecacet.util.introspection.PropertyAccessor;

public class SpringBeanWrapperPropertyAccessor<T> implements PropertyAccessor<T> {

    private final BeanWrapperImpl beanWrapper = new BeanWrapperImpl();

    public SpringBeanWrapperPropertyAccessor() {
        beanWrapper.setAutoGrowNestedPaths(true);
    }

    @Override
    public Object getProperty(T bean, String propertyName) throws BeanIntrospectorException {
        beanWrapper.setWrappedInstance(bean);
        return beanWrapper.getPropertyValue(propertyName);
    }

    @Override
    public void setProperty(T bean, String propertyName, Object value) throws BeanIntrospectorException {
        beanWrapper.setWrappedInstance(bean);
        beanWrapper.setPropertyValue(propertyName, value);
    }

    // TODO temporary
    public BeanWrapperImpl getBeanWrapper() {
        return beanWrapper;
    }

}
