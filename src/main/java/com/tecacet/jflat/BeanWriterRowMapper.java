/*
 Copyright 2008 TecAceT Ltd.

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

import java.util.HashMap;
import java.util.Map;

import com.tecacet.util.conversion.ToStringConverter;
import com.tecacet.util.introspection.PropertyAccessor;
import com.tecacet.util.introspection.jodd.JoddPropertyAccessor;

/**
 * Basic implementation of WriterRowMapper that uses a columnMapping to
 * determine properties to map and a BeanManipulator to set/get the bean
 * properties.
 * 
 * @author Dimitri Papaioannou
 * 
 * @param <T>
 */
public class BeanWriterRowMapper<T> implements WriterRowMapper<T> {

	private ColumnMapping columnMapping;
	private PropertyAccessor<T> propertyAccessor;
	private Map<String, ValueExtractor<T>> extractors = new HashMap<String, ValueExtractor<T>>();
	private Map<Class<?>, ToStringConverter<?>> converters = new HashMap<Class<?>, ToStringConverter<?>>();

	public BeanWriterRowMapper(ColumnMapping columnMapping,
			PropertyAccessor<T> propertyAccessor) {
		this.columnMapping = columnMapping;
		this.propertyAccessor = propertyAccessor;
	}

	public BeanWriterRowMapper(Class<T> type, ColumnMapping mappingStrategy) {
		this(mappingStrategy, new JoddPropertyAccessor<T>());
	}

	public BeanWriterRowMapper(Class<T> type, String[] properties) {
		this(new ColumnPositionMapping(properties),
				new JoddPropertyAccessor<T>());
	}

	public String[] getRow(T bean) {
		String[] row = new String[columnMapping.getNumberOfColumns()];
		for (int i = 0; i < row.length; i++) {
			String property = columnMapping.getProperty(i);
			if (property == null) {
				continue;
			}
			ValueExtractor<T> extractor = extractors.get(property);
			if (extractor == null) {
				row[i] = toString(propertyAccessor.getProperty(bean, property));
			} else {
				row[i] = extractor.getValue(bean);
			}
		}
		return row;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
    private String toString(Object o) {
		if (o == null || o instanceof String) {
			return (String) o;
		}
		ToStringConverter converter = converters.get(o.getClass());
		if (converter != null) {
			return converter.convert(o);
		}
		return o.toString();
	}

	public ColumnMapping getColumnMapping() {
		return columnMapping;
	}

	public void setColumnMapping(ColumnMapping columnMapping) {
		this.columnMapping = columnMapping;
	}

	public void registerValueExtractor(String property,
			ValueExtractor<T> extractor) {
		extractors.put(property, extractor);
	}

	public void deregisterValueExtractor(String property) {
		extractors.remove(property);
	}

	public void registerConverter(Class<?> type, ToStringConverter<?> converter) {
		converters.put(type, converter);
	}

	public void deregisterConverter(Class<?> type) {
		converters.remove(type);
	}

}
