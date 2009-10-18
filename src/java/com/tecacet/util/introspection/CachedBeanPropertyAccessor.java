package com.tecacet.util.introspection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This version of caching which does not work with indexed or mapped properties
 */
public class CachedBeanPropertyAccessor<T> implements PropertyAccessor<T> {

    private static final String DELIM = ".";

    protected Map<String, PropertyDescriptor[]> descriptors = new HashMap<String, PropertyDescriptor[]>();

    protected Log log = LogFactory.getLog(this.getClass());

    protected Class<T> type;

    protected BeanInfo beanInfo;

    public CachedBeanPropertyAccessor(Class<T> type) throws IntrospectionException {
        this.type = type;
        this.beanInfo = Introspector.getBeanInfo(type);
    }

    protected PropertyDescriptor getDescriptor(String propertyName) throws IntrospectionException {

        return getDescriptorForBean(Introspector.getBeanInfo(type), propertyName);
    }

    protected PropertyDescriptor getDescriptorForBean(BeanInfo info, String propertyName) throws IntrospectionException {
        PropertyDescriptor[] descrips = info.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : descrips) {
            if (descriptor.getName().equals(propertyName)) {
                return descriptor;
            }
        }

        // Wasn't in bean, check interfaces
        Class<?>[] interfaces = info.getBeanDescriptor().getBeanClass().getInterfaces();
        if (interfaces != null) {
            for (int i = 0; i < interfaces.length; i++) {
                Class<?> anInterface = interfaces[i];
                PropertyDescriptor desc = getDescriptorForBean(Introspector.getBeanInfo(anInterface), propertyName);
                if (desc != null) {
                    return desc;
                }
            }
        }
        return null;
        // This is disabled because some tables have columns that are members of
        // a subclass of the table content (e.g email address from Locate in
        // table of QueueORders)
        // throw new RuntimeException("Could not find descriptor for property: "
        // + propertyName + " on "
        // + info.getBeanDescriptor().getBeanClass());
    }

    protected void loadProperties(String[] propertyNames) throws IntrospectionException {
        for (String propertyName : propertyNames) {
            loadProperty(propertyName, beanInfo);
        }
    }

    protected PropertyDescriptor[] getDescriptorChain(String propertyName) throws IntrospectionException {
        PropertyDescriptor[] descriptorChain = descriptors.get(propertyName);
        if (descriptorChain == null) {
            return loadProperty(propertyName, beanInfo);
        }
        return descriptorChain;
    }

    protected PropertyDescriptor[] loadProperty(String propertyName, BeanInfo bean) throws IntrospectionException {
        StringTokenizer tokenizer = new StringTokenizer(propertyName, DELIM);
        ArrayList<PropertyDescriptor> chain = new ArrayList<PropertyDescriptor>();
        while (tokenizer.hasMoreTokens()) {
            String prop = tokenizer.nextToken();
            PropertyDescriptor desc = getDescriptorForBean(bean, prop);
            if (desc != null) {
                chain.add(desc);

                if (tokenizer.hasMoreTokens()) {
                    Class<?> type = desc.getPropertyType();
                    bean = Introspector.getBeanInfo(type);
                }
            } else {
                chain = new ArrayList<PropertyDescriptor>();
                break;
            }
        }
        if (chain.size() > 0) {
            PropertyDescriptor[] descriptorArray = chain.toArray(new PropertyDescriptor[0]);
            descriptors.put(propertyName, descriptorArray);
            return descriptorArray;
        } else {
            log.warn("There was no property found for " + propertyName);
            return null;
        }
    }

    @Override
    public Object getProperty(Object bean, String propertyName) throws BeanIntrospectorException {
        if (bean == null) {
            throw new IllegalArgumentException("Bean cannot be null.");
        }
        PropertyDescriptor[] descriptorChain;
        try {
            descriptorChain = getDescriptorChain(propertyName);
        } catch (IntrospectionException e1) {
            throw new BeanIntrospectorException(e1);
        }
        if (descriptorChain == null) {
            return null; // TODO
        }
        Object currentObject = bean;
        for (PropertyDescriptor descriptor : descriptorChain) {
            try {
                currentObject = descriptor.getReadMethod().invoke(currentObject);
            } catch (Exception e) {
                throw new BeanIntrospectorException(e);
            }
        }
        return currentObject;
    }

    @Override
    public void setProperty(Object bean, String propertyName, Object value) throws BeanIntrospectorException {
        if (bean == null) {
            throw new IllegalArgumentException("Bean cannot be null.");
        }
        PropertyDescriptor[] descriptorChain;
        try {
            descriptorChain = getDescriptorChain(propertyName);
        } catch (IntrospectionException e1) {
            throw new BeanIntrospectorException(e1);
        }
        if (descriptorChain == null) {
            return; // TODO
        }
        Object currentObject = bean;

        for (PropertyDescriptor descriptor : descriptorChain) {

            try {
                descriptor.getWriteMethod().invoke(currentObject, value);
            } catch (Exception e) {
                throw new BeanIntrospectorException(e);
            }
        }
    }

    public boolean isValidPath(String propertyPath) {
        return descriptors.get(propertyPath) != null;
    }

}
