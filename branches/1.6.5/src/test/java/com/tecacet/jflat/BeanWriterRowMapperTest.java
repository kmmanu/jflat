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

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.tecacet.jflat.om.Order;
import com.tecacet.util.conversion.DateToStringConverter;
import com.tecacet.util.conversion.DoubleToStringConverter;
import com.tecacet.util.introspection.MapPropertyAccessor;
import com.tecacet.util.introspection.PropertyAccessor;

public class BeanWriterRowMapperTest {

	
	@Test
	public void testGetRow() {
		BeanWriterRowMapper<Order> mapper = new BeanWriterRowMapper<Order>(
				Order.class, new String[] { "number", "price", "quantity",
						"date" });
		mapper.registerConverter(Date.class, new DateToStringConverter(
				"MM/dd/yy"));
		mapper.registerConverter(Double.class, new DoubleToStringConverter("%03.5f"));
		Order order = new Order();
		order.setNumber("911");
		order.setPrice(12.3);
		order.setQuantity(100);
		order.setDate(new GregorianCalendar(1978, 3, 5).getTime());
		String[] row = mapper.getRow(order);
		assertEquals("911", row[0]);
		assertEquals("12.30000", row[1]);
		assertEquals("100", row[2]);
		assertEquals("04/05/78", row[3]);
	}

	@Test
	public void testMapPropertyAccessor() {
		PropertyAccessor<Map<String, Object>> propertyAccessor = new MapPropertyAccessor();
		ColumnMapping columnMapping = new ColumnPositionMapping(new String[] {
				"number", "price", "quantity","date" });
		BeanWriterRowMapper<Map<String, Object>> mapper = new BeanWriterRowMapper<Map<String, Object>>(
				columnMapping, propertyAccessor);
		mapper.registerConverter(Date.class, new DateToStringConverter(
				"MM/dd/yy"));
		mapper.registerConverter(Double.class, new DoubleToStringConverter("%03.5f"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("number", "911");
		map.put("price", 12.3);
		map.put("quantity", 100);
		map.put("date",(new GregorianCalendar(1978, 3, 5).getTime()));
		String[] row = mapper.getRow(map);
		assertEquals("911", row[0]);
		assertEquals("12.30000", row[1]);
		assertEquals("100", row[2]);
		assertEquals("04/05/78", row[3]);
	}
}
