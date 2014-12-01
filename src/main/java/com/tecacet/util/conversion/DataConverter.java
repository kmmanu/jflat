package com.tecacet.util.conversion;

public interface DataConverter<FROM,TO> {

	TO convert(FROM from);
}
