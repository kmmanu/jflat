package com.tecacet.jflat;

/**
 * 
 * @author Dimitri Papaioannou
 *
 * This interface is used by a File Writer to extract a column value from a bean.
 * Suppose that you want to map a currency ($) and an amount (10,000) to a single value that looks like this: $10,000
 * Then you can use a value extractor like this:
 * 
 * <verbatim>
 *  String getValue(Order order) { return order.getCurrency().getSymbol() + order.getAmount() }
 * </verbatim>
 * 
 */
public interface ValueExtractor<T> {

    String getValue(T bean);
}
