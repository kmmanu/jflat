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
