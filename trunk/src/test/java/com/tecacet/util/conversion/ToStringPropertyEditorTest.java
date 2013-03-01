package com.tecacet.util.conversion;

import static org.junit.Assert.*;

import org.junit.Test;

public class ToStringPropertyEditorTest {

	@Test
	public void testGetAsText() {
		DoubleToStringConverter converter = new DoubleToStringConverter("%.3f");
		ToStringPropertyEditor editor = new ToStringPropertyEditor(converter);
		editor.setValue(100.0);
		assertEquals("100.000", editor.getAsText());
	}

}
