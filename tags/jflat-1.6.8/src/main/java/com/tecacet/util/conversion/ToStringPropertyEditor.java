package com.tecacet.util.conversion;

import java.beans.PropertyEditorSupport;

public class ToStringPropertyEditor<T> extends PropertyEditorSupport {

    private ToStringConverter<T> converter;

    public ToStringPropertyEditor(ToStringConverter<T> converter) {
        super();
        this.converter = converter;
    }

    @Override
    public String getAsText() {
        @SuppressWarnings("unchecked")
        T value = (T) getValue();
        if (value == null) {
            return null;
        }
        return converter.convert(value);
    }
}
