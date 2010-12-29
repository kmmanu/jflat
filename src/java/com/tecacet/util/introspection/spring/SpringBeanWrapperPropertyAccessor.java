package com.tecacet.util.introspection.spring;

import java.util.Map;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotWritablePropertyException;

import com.tecacet.util.conversion.ConverterPropertyEditor;
import com.tecacet.util.conversion.ConverterRegistry;
import com.tecacet.util.conversion.GenericConverterRegistry;
import com.tecacet.util.introspection.BeanIntrospectorException;
import com.tecacet.util.introspection.PropertyAccessor;

public class SpringBeanWrapperPropertyAccessor<T> implements PropertyAccessor<T> {

    private final Log log = LogFactory.getLog(this.getClass());

    private final BeanWrapperImpl beanWrapper = new BeanWrapperImpl();
    private boolean skipInvalidProperties = true;

    public SpringBeanWrapperPropertyAccessor() {
        beanWrapper.setAutoGrowNestedPaths(true);
        ConverterRegistry converterRegistry = GenericConverterRegistry.getInstance();
        Map<Class, Converter> converters = converterRegistry.getRegisteredConverters();
        for (Map.Entry<Class, Converter> entry : converters.entrySet()) {
            beanWrapper.registerCustomEditor(entry.getKey(),
                    new ConverterPropertyEditor(entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public Object getProperty(T bean, String propertyName) throws BeanIntrospectorException {
        beanWrapper.setWrappedInstance(bean);
        return beanWrapper.getPropertyValue(propertyName);
    }

    @Override
    public void setProperty(T bean, String propertyName, Object value) throws BeanIntrospectorException {
        beanWrapper.setWrappedInstance(bean);
        try {
            beanWrapper.setPropertyValue(propertyName, value);
        } catch (NotWritablePropertyException e) {
            if (skipInvalidProperties) {
                log.debug(e.getMessage());
            } else {
                throw e;
            }

        }
    }

    // TODO temporary
    public BeanWrapperImpl getBeanWrapper() {
        return beanWrapper;
    }

    public boolean isSkipInvalidProperties() {
        return skipInvalidProperties;
    }

    public void setSkipInvalidProperties(boolean skipInvalidProperties) {
        this.skipInvalidProperties = skipInvalidProperties;
    }

}
