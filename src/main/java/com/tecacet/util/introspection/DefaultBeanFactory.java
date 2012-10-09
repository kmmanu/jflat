package com.tecacet.util.introspection;

/**
 * Default BeanFactory that assumes a constructor without arguments
 * 
 * @author Dimitri Papaioannou
 * 
 * @param <T>
 */
public class DefaultBeanFactory implements BeanFactory {

	public DefaultBeanFactory() {

	}

	@Override
	/**
	 * @throws BeanCreationException if the class does not have a zero argument constructor or if an error occurs when creating the bean
	 */
	public Object createBean(Class<?> type) throws BeanCreationException {
		try {
			return type.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException(e);
		}
	}

}
