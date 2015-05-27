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
package com.tecacet.util.conversion;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.tecacet.util.conversion.BigDecimalToStringConverter;

public class BigDecimalStringConverterTest {

    @Test
    public void testConvertToString() {
       BigDecimalToStringConverter converter = new BigDecimalToStringConverter();
       converter.convert(new BigDecimal(0.00000001)); //NO Exception
       
       converter = new BigDecimalToStringConverter("%03.3f");
       String str = converter.convert(new BigDecimal(67.54));
       assertEquals("67.540", str);
    }

}
