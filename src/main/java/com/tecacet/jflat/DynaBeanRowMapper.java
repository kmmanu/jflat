package com.tecacet.jflat;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;

import com.tecacet.util.introspection.DynaBeanFactory;
import com.tecacet.util.introspection.commons.CommonsBeanUtilsPropertyAccessor;

public class DynaBeanRowMapper extends BeanReaderRowMapper<DynaBean> {

	public DynaBeanRowMapper(DynaClass dynaClass, String[] properties,
			String[] names) {
		super(DynaBean.class,
				new CommonsBeanUtilsPropertyAccessor<DynaBean>(),
				new HeaderColumnNameMapping(properties, names));
		this.setBeanFactory(new DynaBeanFactory(dynaClass));
	}

}
