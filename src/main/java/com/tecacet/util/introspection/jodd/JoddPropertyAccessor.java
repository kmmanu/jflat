package com.tecacet.util.introspection.jodd;

import jodd.bean.BeanUtil;

import com.tecacet.util.introspection.BeanIntrospectorException;
import com.tecacet.util.introspection.PropertyAccessor;

public class JoddPropertyAccessor<T> implements PropertyAccessor<T>{

	@Override
	public Object getProperty(T bean, String propertyName)
			throws BeanIntrospectorException {
		return BeanUtil.getPropertySilently(bean, propertyName);
	}

	@Override
	public void setProperty(T bean, String propertyName, Object value)
			throws BeanIntrospectorException {
		BeanUtil.setPropertyForcedSilent(bean, propertyName, value);
	}

}
