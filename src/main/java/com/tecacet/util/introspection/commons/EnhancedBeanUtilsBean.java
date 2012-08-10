package com.tecacet.util.introspection.commons;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.MappedPropertyDescriptor;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.expression.Resolver;

import com.tecacet.util.introspection.BeanFactory;
import com.tecacet.util.introspection.DefaultBeanFactory;

public class EnhancedBeanUtilsBean extends BeanUtilsBean {

    private static final EnhancedBeanUtilsBean INSTANCE = new EnhancedBeanUtilsBean(new ConvertUtilsBean(), EnhancedPropertyUtilsBean.getInstance());
    
    protected BeanFactory beanFactory = new DefaultBeanFactory();
    
    public static EnhancedBeanUtilsBean getInstance() {
        return INSTANCE;
    }
    
    public EnhancedBeanUtilsBean(ConvertUtilsBean convertUtilsBean, PropertyUtilsBean propertyUtilsBean) {
        super(convertUtilsBean, propertyUtilsBean);
        
    }

    @Override
    public void setProperty(final Object bean, String name, final Object value) throws IllegalAccessException,
            InvocationTargetException {

        Object parent = bean;
        Object target = bean;
        Resolver resolver;
        for (resolver = getPropertyUtils().getResolver(); resolver.hasNested(name);) {
            try {
                target = getPropertyUtils().getProperty(target, resolver.next(name));
                if (target == null) {
                    PropertyDescriptor descriptor = getPropertyUtils().getPropertyDescriptor(parent, resolver.next(name));
                    target = beanFactory.createBean(descriptor.getPropertyType());
                    getPropertyUtils().setSimpleProperty(parent, resolver.next(name), target);
                }
                parent = target;
                name = resolver.remove(name);
            } catch (NoSuchMethodException e) {
                return;
            }
        }
        String propName = resolver.getProperty(name);
        Class type = null;
        int index = resolver.getIndex(name);
        String key = resolver.getKey(name);
        if (target instanceof DynaBean) {
            DynaClass dynaClass = ((DynaBean) target).getDynaClass();
            DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
            if (dynaProperty == null)
                return;
            type = dynaProperty.getType();
        } else if (target instanceof Map) {
            type = java.lang.Object.class;
        } else {
            PropertyDescriptor descriptor = null;
            try {
                descriptor = getPropertyUtils().getPropertyDescriptor(target, name);
                if (descriptor == null)
                    return;
            } catch (NoSuchMethodException e) {
                return;
            }
            if (descriptor instanceof MappedPropertyDescriptor) {
                if (((MappedPropertyDescriptor) descriptor).getMappedWriteMethod() == null) {

                    return;
                }
                type = ((MappedPropertyDescriptor) descriptor).getMappedPropertyType();
            } else if (index >= 0 && (descriptor instanceof IndexedPropertyDescriptor)) {
                if (((IndexedPropertyDescriptor) descriptor).getIndexedWriteMethod() == null) {

                    return;
                }
                type = ((IndexedPropertyDescriptor) descriptor).getIndexedPropertyType();
            } else if (key != null) {
                if (descriptor.getReadMethod() == null) {

                    return;
                }
                type = value != null ? value.getClass() : java.lang.Object.class;
            } else {
                if (descriptor.getWriteMethod() == null) {

                    return;
                }
                type = descriptor.getPropertyType();
            }
        }
        Object newValue = null;
        if (type.isArray() && index < 0) {
            if (value == null) {
                String values[] = new String[1];
                values[0] = (String) value;
                newValue = getConvertUtils().convert((String[]) values, type);
            } else if (value instanceof String)
                newValue = getConvertUtils().convert(value, type);
            else if (value instanceof String[])
                newValue = getConvertUtils().convert((String[]) (String[]) value, type);
            else
                newValue = convert(value, type);
        } else if (type.isArray()) {
            if ((value instanceof String) || value == null)
                newValue = getConvertUtils().convert((String) value, type.getComponentType());
            else if (value instanceof String[])
                newValue = getConvertUtils().convert(((String[]) (String[]) value)[0], type.getComponentType());
            else
                newValue = convert(value, type.getComponentType());
        } else if ((value instanceof String) || value == null)
            newValue = getConvertUtils().convert((String) value, type);
        else if (value instanceof String[])
            newValue = getConvertUtils().convert(((String[]) (String[]) value)[0], type);
        else
            newValue = convert(value, type);
        try {
            getPropertyUtils().setProperty(target, name, newValue);
        } catch (NoSuchMethodException e) {
            throw new InvocationTargetException(e, "Cannot set " + propName);
        }
    }
}
