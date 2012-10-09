package com.tecacet.util.introspection;

import org.apache.commons.beanutils.DynaClass;

public class DynaBeanFactory implements BeanFactory {

	private final DynaClass dynaClass;

	public DynaBeanFactory(DynaClass dynaClass) {
		super();
		this.dynaClass = dynaClass;
	}

	@Override
	/**
	 * @param type is ignored
	 */
	public Object createBean(Class<?> type) throws BeanCreationException {
		try {
			return dynaClass.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException(e);
		}
	}

}
