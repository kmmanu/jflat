package com.tecacet.util.introspection.commons;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;


/**
 * We need to ensure that our PropertyUtilsBean is created it is caching the
 * PropertyDescriptors
 */
public class PropertyUtils extends org.apache.commons.beanutils.PropertyUtils {

    static {
        BeanUtilsBean.setInstance(new BeanUtilsBean(new ConvertUtilsBean(), new EnhancedPropertyUtilsBean()));
    }

}
