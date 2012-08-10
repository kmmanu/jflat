package com.tecacet.util.conversion;

import java.beans.PropertyEditorSupport;

public class ToStringPropertyEditor extends PropertyEditorSupport {

    private ToStringConverter converter;

    public ToStringPropertyEditor(ToStringConverter converter) {
        super();
        this.converter = converter;
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        if (value == null) {
            return null;
        }
        return converter.convertToString(value);
    }
}
