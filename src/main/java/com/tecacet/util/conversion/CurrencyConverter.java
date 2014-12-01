package com.tecacet.util.conversion;

import java.util.Currency;

/**
 * Converts a currency code to a Currency object
 */
public class CurrencyConverter implements DataConverter<String, Currency> {

   
	@Override
	public Currency convert(String from) {
		if (from == null) {
            return null;
        }
        return Currency.getInstance(from);
	}

}
