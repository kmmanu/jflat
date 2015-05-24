/*
 Copyright 2008  TecaceT Ltd.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.tecacet.jflat;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;

import com.tecacet.util.introspection.DynaBeanFactory;
import com.tecacet.util.introspection.jodd.JoddPropertyAccessor;

public class DynaBeanRowMapper extends BeanReaderRowMapper<DynaBean> {

	public DynaBeanRowMapper(DynaClass dynaClass, String[] properties,
			String[] names) {
		super(DynaBean.class,
				new JoddPropertyAccessor<DynaBean>(),
				new HeaderColumnNameMapping(properties, names));
		this.setBeanFactory(new DynaBeanFactory(dynaClass));
	}

}
