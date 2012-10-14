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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.Converter;

import com.tecacet.util.introspection.BeanFactory;
import com.tecacet.util.introspection.BeanIntrospectorException;
import com.tecacet.util.introspection.DefaultBeanFactory;
import com.tecacet.util.introspection.PropertyAccessor;

import com.tecacet.util.introspection.spring.SpringBeanWrapperPropertyAccessor;

/**
 * Basic implementation of ReaderRowMapper that uses a columnMapping to
 * determine properties to map and a BeanManipulator to set/get the bean
 * properties.
 * 
 * @author Dimitri Papaioannou
 * 
 * @param <T>
 */
public class BeanReaderRowMapper<T> implements ReaderRowMapper<T> {

	private final int headerRow;

	private ColumnMapping columnMapping;
	private PropertyAccessor<T> propertyAccessor;
	private BeanFactory beanFactory = new DefaultBeanFactory();
	private Class<T> type;

	private Map<String, Converter> converters = new HashMap<String, Converter>();

	/**
	 * Construct a rowMapper using a BeanManipulator to create beans and
	 * populate properties and a column mapping to determine which properties to
	 * map to columns
	 * 
	 * @param beanManipulator
	 * @param columnMapping
	 */
	public BeanReaderRowMapper(Class<T> type,
			PropertyAccessor<T> propertyAccessor, ColumnMapping columnMapping) {
		this.columnMapping = columnMapping;
		this.propertyAccessor = propertyAccessor;
		this.type = type;
		this.headerRow = 1;
	}

	public BeanReaderRowMapper(Class<T> type,
			PropertyAccessor<T> propertyAccessor, ColumnMapping columnMapping,
			int headerRow) {
		this.columnMapping = columnMapping;
		this.propertyAccessor = propertyAccessor;
		this.type = type;
		this.headerRow = headerRow;
	}

	/**
	 * Construct a rowMapper using a the default BeanManipulator and a column
	 * mapping to determine which properties to map to columns
	 * 
	 * @param type
	 * @param columnMapping
	 */
	public BeanReaderRowMapper(Class<T> type, ColumnMapping columnMapping) {
		this(type, new SpringBeanWrapperPropertyAccessor<T>(), columnMapping);
	}

	/**
	 * Construct a rowMapper using a the default BeanManipulator The mapping
	 * strategy is HeaderColumnNameMapping.
	 * 
	 * @param type
	 * @param map
	 *            A map from column names to properties
	 */
	public BeanReaderRowMapper(Class<T> type, Map<String, String> columns) {
		this(type, new SpringBeanWrapperPropertyAccessor<T>(),
				new HeaderColumnNameMapping(columns));
	}

	public BeanReaderRowMapper(Class<T> type, String[] properties,
			String[] header) {
		this(type, new SpringBeanWrapperPropertyAccessor<T>(),
				new HeaderColumnNameMapping(properties, header));
	}

	/**
	 * Returns a bean constructed and populated using the BeanManipulator.
	 * WARNING: This implementation returns null for the header row if the
	 * ColumnMapping requires a header row.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getRow(String[] row, int rowNumber)
			throws BeanIntrospectorException {
		if (rowNumber == headerRow) {
			if (columnMapping.requiresHeaderRow()) {
				columnMapping.setHeaderRow(row);
				return null;
			}
		}
		T bean = (T) beanFactory.createBean(type);
		for (int i = 0; i < row.length; i++) {
			String property = columnMapping.getProperty(i);
			if (property == null) {
				continue;
			}
			Converter converter = converters.get(property);
			if (converter != null) {
				Object value = converter.convert(null, row[i]);
				propertyAccessor.setProperty(bean, property, value);
			} else {
				propertyAccessor.setProperty(bean, property, row[i]);
			}
		}
		return bean;
	}

	public ColumnMapping getColumnMapping() {
		return columnMapping;
	}

	public void setColumnMapping(ColumnMapping columnMapping) {
		this.columnMapping = columnMapping;
	}

	public int getHeaderRow() {
		return headerRow;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public PropertyAccessor<T> getPropertyAccessor() {
		return propertyAccessor;
	}

	public void setPropertyAccessor(PropertyAccessor<T> propertyAccessor) {
		this.propertyAccessor = propertyAccessor;
	}

	public void registerConverter(String property, Converter converter) {
		converters.put(property, converter);
	}
}