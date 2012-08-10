package com.tecacet.util.introspection;

import java.util.Map;

/**
 * Default property accessor for a map 
 * 
 * @author Dimitri Papaioannou
 *
 */
public class MapPropertyAccessor implements PropertyAccessor<Map<String, Object>> {

    @Override
    public Object getProperty(Map<String, Object> bean, String propertyName) throws BeanIntrospectorException {
        return bean.get(propertyName);
    }

    @Override
    public void setProperty(Map<String, Object> bean, String propertyName, Object value)
            throws BeanIntrospectorException {
        bean.put(propertyName, value);
    }

}
