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

import org.junit.Test;

import com.tecacet.jflat.om.Order;

public class BeanWriterRowMapperTest {

    @Test
    public void testGetRow() {
        BeanWriterRowMapper<Order> mapper = new BeanWriterRowMapper<Order>(Order.class, new String[] { "number",
                "price","quantity" });
        
        Order order = new Order();
        order.setNumber("911");
        order.setPrice(12.3);
        order.setQuantity(100);
        String[] row = mapper.getRow(order);
        assertEquals("911", row[0]);
        assertEquals("12.3", row[1]);
        assertEquals("100", row[2]);

    }

}
