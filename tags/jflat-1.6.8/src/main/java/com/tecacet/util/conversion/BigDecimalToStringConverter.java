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

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalToStringConverter implements ToStringConverter<BigDecimal> {

    private static final String FLOAT_FORMAT = "%019.5f";
    private transient final String format;

    public BigDecimalToStringConverter() {
        this(FLOAT_FORMAT);
    }

    public BigDecimalToStringConverter(String format) {
        this.format = format;
    }

    @Override
    public String convert(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        //There is a Java bug when formatting certain big decimals:
        // http://bugs.sun.com/view_bug.do?bug_id=6476425
        BigDecimal safe = amount.setScale(5, RoundingMode.HALF_EVEN);
        return String.format(format, safe);
    }

}
