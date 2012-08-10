package com.tecacet.util.introspection.commons;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.expression.Resolver;

import com.tecacet.util.introspection.BeanCreationException;
import com.tecacet.util.introspection.BeanFactory;
import com.tecacet.util.introspection.DefaultBeanFactory;

/**
 * Extending org.apache.commons.beanutils.PropertyUtilsBean to add the method
 * getNestedPropertyAllowNulls This method allows use to access a nest property
 * without throwing a NPE if a nested reference to a property returns null. In
 * this case a null will be returned
 * 
 * We need to subclass PropertyUtilsBean because it is caching the
 * PropertyDescriptors
 * 
 */

public class EnhancedPropertyUtilsBean extends org.apache.commons.beanutils.PropertyUtilsBean {

    protected BeanFactory beanFactory = new DefaultBeanFactory();

    private static final EnhancedPropertyUtilsBean INSTANCE = new EnhancedPropertyUtilsBean();
    
    public static EnhancedPropertyUtilsBean getInstance() {
        return INSTANCE;
    }
    
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    /**
     * 
     */
    public void setNestedProperty(Object bean, String name, Object value) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, BeanCreationException {
        Resolver resolver = this.getResolver();
        if (bean == null) {
            throw new IllegalArgumentException("No bean specified");
        }
        if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        }
        for (; resolver.hasNested(name); name = resolver.remove(name)) {
            String next = resolver.next(name);
            Object nestedBean = null;
            if (bean instanceof Map) {
                nestedBean = getPropertyOfMapBean((Map) bean, next);
            } else if (resolver.isMapped(next)) {
                nestedBean = getMappedProperty(bean, next);
            } else if (resolver.isIndexed(next)) {
                nestedBean = getIndexedProperty(bean, next);
            } else {
                nestedBean = getSimpleProperty(bean, next);
            }
            if (nestedBean == null) {
                PropertyDescriptor descriptor = getPropertyDescriptor(bean, next);
                nestedBean = beanFactory.createBean(descriptor.getPropertyType());
                setSimpleProperty(bean, next, nestedBean);
            }
            bean = nestedBean;
        }

        if (bean instanceof Map) {
            setPropertyOfMapBean((Map) bean, name, value);
        } else if (resolver.isMapped(name)) {
            setMappedProperty(bean, name, value);
        } else if (resolver.isIndexed(name)) {
            setIndexedProperty(bean, name, value);
        } else {
            setSimpleProperty(bean, name, value);
        }
    }

    public Object getNestedProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Resolver resolver = this.getResolver();
        if (bean == null)
            throw new IllegalArgumentException("No bean specified");
        if (name == null)
            throw new IllegalArgumentException("No name specified for bean class '" + bean.getClass() + "'");
        for (; resolver.hasNested(name); name = resolver.remove(name)) {
            String next = resolver.next(name);
            Object nestedBean = null;
            if (bean instanceof Map)
                nestedBean = getPropertyOfMapBean((Map) bean, next);
            else if (resolver.isMapped(next))
                nestedBean = getMappedProperty(bean, next);
            else if (resolver.isIndexed(next))
                nestedBean = getIndexedProperty(bean, next);
            else
                nestedBean = getSimpleProperty(bean, next);
            if (nestedBean == null)
                throw new NestedNullException("Null property value for '" + name + "' on bean class '"
                        + bean.getClass() + "'");
            bean = nestedBean;
        }

        if (bean instanceof Map)
            bean = getPropertyOfMapBean((Map) bean, name);
        else if (resolver.isMapped(name))
            bean = getMappedProperty(bean, name);
        else if (resolver.isIndexed(name))
            bean = getIndexedProperty(bean, name);
        else
            bean = getSimpleProperty(bean, name);
        return bean;
    }

}
