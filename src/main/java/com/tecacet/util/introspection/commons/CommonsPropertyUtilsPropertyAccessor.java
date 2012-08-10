package com.tecacet.util.introspection.commons;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.NestedNullException;

import com.tecacet.util.introspection.BeanIntrospectorException;
import com.tecacet.util.introspection.PropertyAccessor;

/**
 * Implementation of BeanIntrospector that uses commons-beanutils
 * 
 */
public class CommonsPropertyUtilsPropertyAccessor<T> implements PropertyAccessor<T> {

    
    static {
        BeanUtilsBean.setInstance(EnhancedBeanUtilsBean.getInstance());
    }

    public Object getProperty(T bean, String propertyName) throws BeanIntrospectorException {
        try {
            return PropertyUtils.getProperty(bean, propertyName);
        } catch (NestedNullException nne) {
            return null;
        } catch (IllegalAccessException e) {
            throw new BeanIntrospectorException(e);
        } catch (InvocationTargetException e) {
            throw new BeanIntrospectorException(e);
        } catch (NoSuchMethodException e) {
            throw new BeanIntrospectorException(e);
        }
    }

    public void setProperty(T bean, String propertyName, Object value) throws BeanIntrospectorException {
        try {
            PropertyUtils.setProperty(bean, propertyName, value);
        } catch (IllegalAccessException e) {
            throw new BeanIntrospectorException(e);
        } catch (InvocationTargetException e) {
            throw new BeanIntrospectorException(e);
        } catch (NoSuchMethodException e) {
            throw new BeanIntrospectorException(e);
        }
    }

}
